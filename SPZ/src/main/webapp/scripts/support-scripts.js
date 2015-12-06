/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function checkField(field,maxLen,whereError){
    if(length(field.text.get)>maxLen){
        whereError = "Muze obsahovat "+maxLen +" znaku. Obsahuje " + length(field.text)+".";
    }
}

