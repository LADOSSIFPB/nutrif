/*
 *  Função para transformar um array em uma matriz.
 */
nutrIFApp.factory("arrayUtil", function ($mdToast) {
    return {
        isEmpty: function (array, columnsLength) {
            
            let isEmpty = true;
            
            if(typeof array != "undefined" 
               && array != null 
               && array.length != null 
               && array.length > 0){
                
                isEmpty = false;
            }
            
            return isEmpty;
        }
    }
});
