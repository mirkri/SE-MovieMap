//source http://programming-tips.in/capture-section-of-web-page-and-download-as-an-image/
function captureDiv() { 
	$('#regions_div').html2canvas({ 
		// Map-Div goes here
		 onrendered: function (canvas) { 
                      Canvas2Image.saveAsPNG(canvas);  			 
                 }
	}); 
 }