/*
 *  Funções de gerenciamento do Toast.
 */
nutrIFApp.factory("dateTimeUtil", function ($mdToast) {
    return {
        timeToDate: function (timeStr) {
            var now = new Date();
            var res = timeStr.split(":");
            now.setHours(res[0]);
            now.setMinutes(res[1]);
            now.setSeconds(0);
            return now;
        }
    }
});