function StringTokenizer(str, separator) {
    var _this = this;
    this.str = str;
    this.separator = separator;
    this.currentPosition = 0;
    this.options = null;
    var constructor = function (str, separator) {
        _this.options = str.split(separator);
    }
    constructor(str, separator);
}
StringTokenizer.prototype.nextToken = function () {
    var currentToken = this.options[this.currentPosition];
    this.currentPosition = this.currentPosition + 1;
    return currentToken;
}
StringTokenizer.prototype.hasMoreTokens = function () {
    return this.currentPosition < this.options.length;
}
StringTokenizer.prototype.position = function (str) {
    for (var i in this.options) {
        if (str === this.options[i]) {
            return i;
        }
    }
    return -1;
}
