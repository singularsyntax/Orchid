---
from: docs.plugin_index
description: Load the full text of Bible verses quickly and easily.
images:
  - src: https://res.cloudinary.com/orchid/image/upload/c_scale,w_300,e_blur:150/v1524973072/plugins/bible.jpg
    alt: Bible
    caption: Photo by Priscilla Du Preez on Unsplash
tags:
    - markup
---

## About

OrchidBible makes it easy to embed Bible verses in your site content. Just pass in the reference to a verse and Orchid
will look up the verse text and add it to your site automatically.

## Demo

{% if site.isProduction() %}> {{ "John 3:16"|bible("eng-KJV") }}{% endif %}

{% if site.isProduction() %}> {{ bible("Galatians 2 19-21", "eng-NASB") }}{% endif %}

## Usage

### Basic Usage

Bible verses can be added simply by passing their reference to the `bible` function. The verse text will be downloaded 
and displayed on the page, along with the verse reference. The Bible version must be the `id` of one of the 
[available versions on Bibles.org](https://www.bibles.org/versions_api).

As a filter

```jinja
{% verbatim %}
{{ "John 3:16"|bible("eng-KJV") }}
{%- endverbatim %}
```

As a function

```jinja
{% verbatim %}
{{ bible("Galatians 2 19-21", "eng-NASB") }}
{%- endverbatim %}
```

### Configuration

You will need to sign up for an API key for the [Bibles.org API](https://www.bibles.org/pages/api) to download verse 
text. Once you create and account and get an API key, add it to Orchid using the `--absApiKey` CLI flag, or set it as an
environment variable at `absApiKey`.

```groovy
orchid {
    ...
    args += ["--absApiKey", "${project.property('ABS_API_KEY')}"]
}
```

You can also set the default Bible version with the `--absDefaultVersion` flag. This will be the version used if you do
not pass one to the `bible()` function.

```groovy
orchid {
    ...
    args += ["--absDefaultVersion", "eng-NASB"]
}
```
