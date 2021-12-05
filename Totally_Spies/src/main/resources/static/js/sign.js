jQuery(document).ready(function( $ ) {
    $.ajaxSetup({
        beforeSend : function(xhr, settings) {
            if (settings.type == 'POST' || settings.type == 'PUT'
                || settings.type == 'DELETE') {
                if (!(/^http:.*/.test(settings.url) || /^https:.*/
                    .test(settings.url))) {
                    // Only send the token to relative URLs i.e. locally.
                    xhr.setRequestHeader("X-XSRF-TOKEN", Cookies
                        .get('XSRF-TOKEN'));
                }
            }
        }
    });
    var logout = function() {
        $.post("/logout", function() {
            $("#user").html('');
            $(".unauthenticated").show();
            $(".authenticated").hide();
        })
        return true;
    }
});


window.onload = function()
{
    add = document.getElementById("add_element");
    form = document.getElementById("Form");

    console.log(form);
    form.addEventListener("submit", validateForm);


    add.addEventListener("click", function() {
        interestElement = document.getElementById("user_interest");
        selectInterest = document.getElementById("interest_list");
        interestL = document.getElementsByClassName("interest-info");

        console.log(interestL);
        if (selectInterest.value && selectInterest.value.trim().length > 1) {
            for (let i = 0; i < interestL.length; i++) {
                console.log(selectInterest.value);
                console.log(interestL[i].innerHTML);
                console.log("Next");
                if (selectInterest.value === interestL[i].innerHTML)
                    return;
            };
            let newNode = document.createElement("p");
            newNode.innerHTML = selectInterest.value;
            newNode.className = "interest-info";
            interestElement.appendChild(newNode);
        }
    })

    function validateForm(event)
    {
        pass = document.getElementById("password");
        passVer = document.getElementById("password_verification");
        if (passVer.value !== pass.value) {
            event.preventDefault();
        }
        name = document.getElementById("personName").value;
        interests = document.getElementsByClassName("interest-info");
        let listOfObjects = [];
        for (let i = 0; i < interests.length; i++) {
            listOfObjects.push(interests[i].innerHTML);
        }
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8081/register", true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        var data = new FormData();
        data.append('personName', 'person');
        data.append("test", "te");

        var params = {
            "personName": name,
            "password": pass.value,
            "interest": listOfObjects
        }

        xhr.send(JSON.stringify(params));
    }
}