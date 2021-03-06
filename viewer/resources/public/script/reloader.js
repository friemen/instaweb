
// repeatedly requests content from interactive dev support
// and replaces <div id='content'/>
// requires JQuery

lastContent = "";

function setContent(content) {
    if (lastContent != content) {
        $("#content").replaceWith(
            "<div id='content'>" + content + "</div>"
        );
    }
}

function setStyle(style) {
    $("#mainStyle").replaceWith(
	"<style id='mainStyle'>\n" + style + "</style>"
    );
}
    

function waitForContent() {
    $.ajax({type: "GET",
            url: "/refresh",
            async: true, /* If set to non-async, browser shows page as "Loading.."*/
            cache: false,
            timeout: 50000, /* Timeout in ms */
	    dataType: "json",
            success: function(data) {
                setContent(data.content);
		setStyle(data.style);
                setTimeout(
                    waitForContent,
                    2000 /* 3 seconds */
                );
            },
            error: function(request, textStatus, errorThrown) {
                setContent(textStatus + " (" + errorThrown + ")");
                setTimeout(
                    waitForContent,
                    15000); /* 15 seconds */
            }
	   });
};

$(document).ready(function() {
    waitForContent(); 
});
