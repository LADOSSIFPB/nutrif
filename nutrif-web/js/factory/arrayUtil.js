/*
 *  Funções de gerenciamento do Toast.
 */
nutrifApp.factory("arrayUtil", function ($mdToast) {
    return {
        /**
         * @description determine if an array contains one or more items from another array.
         * @param {array} haystack the array to search.
         * @param {array} arr the array providing items to check for in the haystack.
         * @return {boolean} true|false if haystack contains at least one item from arr.
         */
        findOne: function (myArray, otherArray) {
            return otherArray.some(function (v) {
                return myArray.indexOf(v) >= 0;
            });
        },

        findString: function (words, word) {
            return words.indexOf(word) >= 0;
        },

        /*
         * function(a){ return a.id == some_id_you_want }
         */
        find: function (myArray, functionFilter) {
            return myArray.filter(functionFilter);
        }
    }
});