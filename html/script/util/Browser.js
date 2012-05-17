function Browser() {
}
Browser.getLocale = function() {
    var language = window.navigator.language;
    if (language == null) {
        language = window.navigator.userLanguage;
    }
    var index = language.indexOf('-');
    return language.substring(0, index).toLowerCase() + "_" + language.substring(index + 1).toUpperCase();
}