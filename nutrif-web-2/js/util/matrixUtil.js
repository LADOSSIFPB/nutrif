/*
 *  Função para transformar um array em uma matriz.
 */
nutrIFApp.factory("matrixUtil", function ($mdToast) {
    return {
        arrayToMatrix: function (array, columnsLength) {
            
            // O resto da divisão do tamanho do array pela tamanho da coluna da matriz
            var rest = array.length % columnsLength;
            
            // A quantidade de interações necessárias para inserir todo o array dentro dessa matriz
            var tempInteracoes = Math.floor(array.length/columnsLength);
            
            // Correção na quantidade de interações caso o resultado na variavel rest não tenha sido zero
            var interacoes = (rest==0?tempInteracoes:tempInteracoes+1);
            
            // Criação da matriz que vai ser retornada
            var matrix = [];
            
            // Populando a matriz
            for (var x = 0; x < interacoes; x++) {
                var startPoint = columnsLength*x;
                var endPoint = columnsLength*x + columnsLength;
                matrix.push(array.slice(startPoint, endPoint)); 
            }
            
            return matrix;
        }
    }
});
