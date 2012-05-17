function I18nManager(url) {
    this.locale = Browser.getLocale();
    this.url = url;
    this.resourceBundleCache = {};
}
I18nManager.prototype.setLocale = function(locale) {
    this.locale = locale;
}
I18nManager.prototype.loadResource = function(url, locale) {

}
I18nManager.prototype.toLocale = function(resource) {

}