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

function doPost(form){
    console.log("doing " + form.method +" to " + form.action);
    var method = form.method;
    if(method.localeCompare("post")!==0){
        form.method = "post";
        console.log("setting method to post.");
    }else{
        console.log("Method was post.");
    }
    
    //document.appendChild(form);
    form.submit();
}

