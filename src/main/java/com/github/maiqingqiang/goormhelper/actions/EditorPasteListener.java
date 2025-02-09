package com.github.maiqingqiang.goormhelper.actions;

import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.services.GoORMHelperProjectSettings;
import com.github.maiqingqiang.goormhelper.sql2struct.ISQL2Struct;
import com.github.maiqingqiang.goormhelper.ui.ConvertSettingDialogWrapper;
import com.goide.psi.GoFile;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import net.sf.jsqlparser.util.validation.Validation;
import net.sf.jsqlparser.util.validation.ValidationError;
import net.sf.jsqlparser.util.validation.feature.FeaturesAllowed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.DataFlavor;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EditorPasteListener extends EditorActionHandler {

    private final EditorActionHandler handler;

    public EditorPasteListener(EditorActionHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {

        PsiFile file = CommonDataKeys.PSI_FILE.getData(dataContext);

        if ((file instanceof GoFile)) {
            String text = CopyPasteManager.getInstance().getContents(DataFlavor.stringFlavor);

            if (verifySQL(text)) {
                Project project = editor.getProject();

                GoORMHelperProjectSettings.State state = Objects.requireNonNull(GoORMHelperProjectSettings.getInstance(Objects.requireNonNull(project)).getState());

                Types.ORM selectedORM = state.defaultORM;
                Types.Database selectedDatabase = state.defaultDatabase;

                if (selectedORM == Types.ORM.AskEveryTime || selectedDatabase == Types.Database.AskEveryTime) {
                    ConvertSettingDialogWrapper wrapper = new ConvertSettingDialogWrapper(project);
                    if (!wrapper.showAndGet()) {
                        this.handler.execute(editor, caret, dataContext);
                        return;
                    }

                    selectedORM = (Types.ORM) wrapper.getOrmComponent().getComponent().getSelectedItem();
                    selectedDatabase = (Types.Database) wrapper.getDatabaseComponent().getComponent().getSelectedItem();
                }

                final Types.ORM finalSelectedORM = selectedORM;
                final Types.Database finalSelectedDatabase = selectedDatabase;

                WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> {
                    if (text == null || text.isEmpty() || finalSelectedORM == null || finalSelectedDatabase == null)
                        return;

                    ISQL2Struct sql2Struct = finalSelectedORM.sql2Struct(text, finalSelectedDatabase.toDbType());

                    Caret currentCaret = editor.getCaretModel().getCurrentCaret();
                    int start = currentCaret.getSelectionStart();

                    editor.getDocument().insertString(start, sql2Struct.convert());
                });

                return;
            }
        }

        this.handler.execute(editor, caret, dataContext);
    }

    private boolean verifySQL(String sql) {
        try {
            Validation validation = new Validation(Collections.singletonList(FeaturesAllowed.CREATE), sql);
            List<ValidationError> errors = validation.validate();

            return errors.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

}
