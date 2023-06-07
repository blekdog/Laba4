let Food = {
    firstMeal: "",
    secondMeal: "",
    thirdMeal: "",
    fourthMeal: "",
    weight : 0,
    idInDatabase: 0
}
function sendEditedFood(){
    let xhrEdit = new XMLHttpRequest();
    Food.title=document.getElementById(" firstMeal").value;
    Food.genre=document.getElementById(" secondMeal").value;
    Food.author=document.getElementById("thirdMeal").value;
    Food.studio=document.getElementById("fourthMeal").value;
    Food.amount=document.getElementById("weight").value;
    Food.idInDatabase=location.search.split("=")[1];
    let seriesJson = JSON.stringify(Food);
    console.log(Food);
    xhrEdit.open("POST", "save", true);
    xhrEdit.setRequestHeader('Content-Type', 'application/json');
    xhrEdit.send(seriesJson);
    location.href="read"
}