/**
 * Funtion implements client-side confirm password validation 
 *
 */
 
 function checkPwd(input) {
    if (input.value != document.getElementById('pwd').value) {
        input.setCustomValidity('Password Must be Matching.');
    }
    else {
        input.setCustomValidity('');
    }
}