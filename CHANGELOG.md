<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Go ORM Helper Changelog

## [Unreleased]

## [1.2.4] - 2023-08-13

### Changed
- Use VfsUtilCore.iterateChildrenRecursively
- Use ProjectUtil.guessProjectDir()
- Use Strings.toSnakeCase instead of com.google.common.base.CaseFormat && plugin.xml use postStartupActivity
- Remove log
- Use com.intellij.lang.annotation.Annotator instead of com.intellij.codeInsight.daemon.impl.HighlightVisitor #12
- Remove plugin.xml projectService node
- Use StartupActivity instead of ProjectManagerListener

### Fixed
- Fix Dao Completion
- Fix ScannedPath
- Fix FileChooserDescriptor.chooseMultiple

## [1.2.3] - 2023-08-07

### Fixed
- Fix "com.intellij.diagnostic.PluginException: Cannot init component state"

## [1.2.2] - 2023-08-06

### Fixed
- When using the DAO tool to generate a model, the field names are directly used as saved in the DAO. #9

## [1.2.1] - 2023-07-25

### Added
- Support Inline Conditions.

### Fixed
- Fix GoFrame ORM unsupported syntax.
- Fix Outdated stub in index Exception.

## [1.2.0] - 2023-07-15

### Added
- Support GoFrame ORM.
- Support Go ORM Tags Live Template.
- Refactor Code Completion. Better Performance!
- Refactor GoORMHelper Cache Manager.

## [1.1.0] - 2023-07-04

### Added
- Assisted code completion with @Table annotation.
- Support ORM's table method.

### Fixed
- Fix SQL to Struct error.

## [1.0.2] - 2023-07-04

### Fixed
- Fix the verify SQL exception.

## [1.0.1] - 2023-07-03

### Fixed
- Fix the issue where the scanning paths in the settings cannot utilize the delete, move up, move down, and other
  functions.

### Optimize
- Abstract the core path of the ORM into a common class.
- Implement multi-language support, currently supporting Chinese, English, Japanese, and Korean languages.
- Separate the plugin description.

## [1.0.0] - 2023-07-02

### Added
- ORM Code Completion
- SQL to Struct

[Unreleased]: https://github.com/maiqingqiang/go-orm-helper/compare/v1.2.4...HEAD
[1.2.4]: https://github.com/maiqingqiang/go-orm-helper/compare/v1.2.3...v1.2.4
[1.2.3]: https://github.com/maiqingqiang/go-orm-helper/compare/v1.2.2...v1.2.3
[1.2.2]: https://github.com/maiqingqiang/go-orm-helper/compare/v1.2.1...v1.2.2
[1.2.1]: https://github.com/maiqingqiang/go-orm-helper/compare/v1.2.0...v1.2.1
[1.2.0]: https://github.com/maiqingqiang/go-orm-helper/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/maiqingqiang/go-orm-helper/compare/v1.0.2...v1.1.0
[1.0.2]: https://github.com/maiqingqiang/go-orm-helper/compare/v1.0.1...v1.0.2
[1.0.1]: https://github.com/maiqingqiang/go-orm-helper/compare/v1.0.0...v1.0.1
[1.0.0]: https://github.com/maiqingqiang/go-orm-helper/tree/v1.0.0
