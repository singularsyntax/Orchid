---
from: docs.plugin_index
description: Auto-generate your plugin's documentation, plus a handy admin panel to bring your plugins documentation to you.
images:
  - src: https://res.cloudinary.com/orchid/image/upload/c_scale,w_300,e_blur:150/v1524974694/plugins/plugindocs.jpg
    alt: Plugin Docs
    caption: Photo by Daniel McCullough on Unsplash
tags:
    - docs
    - cms
    - components
---

## About

OrchidPluginDocs is the solution to out-of-date documentation. It adds an admin panel to Orchid which automatically 
documents about available features and their possible options for all plugins included in your build. 

The same auto-documentation can be used to produce similar static documentation, for example, for documenting your own
Orchid plugin.

## Demo

- Try the [starter app](https://github.com/JavaEden/OrchidStarter) and visit `http://localhost:8080/admin` 
- Read the {{ anchor('Amazing Admin Panel', 'The Amazing, Auto-Documenting Admin Panel') }} tutorial 

## Usage

### Documenting your Orchid Plugin

The Orchid Plugin Docs plugin makes it dead-simple to document much of the information that is easily forgotten and not
ket up-to-date when developing plugins. It is common to write a short description of your plugin and how to use it, but
over time, the specifics of the names of your options, new classes, etc. are often lost and documentation is not updated
to reflect these changes. While it isn't possible to solve this problem across arbitrary codebases, Orchid does know a
lot about your plugin, and can automate this for your Orchid plugins. 

The simplest way for Orchid to document your plugin is to use the `pluginDocs` component and pass it your root package.
It will then look through all the classes in that package and its sub-packages, and render a table of all the Options 
on that class, including their types and default values. You may pass multiple packages and they all will be documented 
in the same way, if you want to be a bit more prescriptive about the packages to document.

```yaml

---
layout: frontPage
components:
  - type: pluginDocs
    tableClass: table
    tableLeaderClass: hidden
    packageNames: 
      - com.eden.orchid.plugindocs
---
```

If your want more control over the exact classes that are used, you may set the specific classes (fully-qualified class
names) to the `pluginDocs` component's `classNames` property, instead of using `packageNames`. Again, you may pass 
multiple class names to document them all, and you can combine specific classes with whole packages. 

You may also find it useful to include the options table directly in written content, rather than just as a Component.
For these cases, you may use the `docs` Tag and pass it the fully-qualified name of a class to document. 

### Bringing your Plugins' Documentation to You

While the `pluginDocs` component and `docs` tag are intended for use by plugin and theme developers, the Orchid Plugin 
Docs also comes with an admin panel that is useful for anyone running Orchid locally, an admin panel that brings the 
options for your currently installed plugins to you in one place. You should still visit your plugins' documentation to
learn how to use them, but for a quick reference on the options and basic usage of your plugins, you can view 
`http://localhost:8080/admin` to see it all in once place. 

In this panel, you can view the important class types currently registered with your Orchid site, _all_ extensible 
classes' used with your Orchid site, and see basic usage for objects of that type. It also includes a command bar for 
you to execute Orchid Commands. 