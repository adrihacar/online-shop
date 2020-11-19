

function displayError(){	

	const error = document.querySelector(".error-desc");
	let i = 0, data = "", text = error.getAttribute("data-text");

	let typing = setInterval(() => {
	  if(i == text.length){
	    clearInterval(typing);
	  }else{
	    data += text[i] ;
	    document.querySelector(".error-desc").setAttribute("data-text", data);
	    i++;	    
	  }
	}, 50);	


}

