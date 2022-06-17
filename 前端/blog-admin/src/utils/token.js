function setToken(value) {
    localStorage.setItem("Token", value);
}

function getToken() {
    return localStorage.getItem("Token");
}
function removeToken(){
    localStorage.removeItem("Token")
}
export {setToken,getToken,removeToken}