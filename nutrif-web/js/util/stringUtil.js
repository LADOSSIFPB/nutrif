/*
 *  Funções de gerenciamento do Toast.
 */
nutrIFApp.factory("stringUtil", function ($mdToast) {
    return {

        isEmpty: function (e) {
            switch (e) {
                case "":
                case 0:
                case "0":
                case null:
                case false:
                case typeof this == "undefined":
                    return true;
                default:
                    return false;
            }
        }
    }
});