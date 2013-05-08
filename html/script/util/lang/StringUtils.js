String.prototype.leftTrim = function () {
    return this.replace(/^\s+/, "");
}
String.prototype.rightTrim = function () {
    return this.replace(/\s+$/, "");
}
String.prototype.trim = function () {
    return this.replace(/^\s+|\s+$/g, "");
}