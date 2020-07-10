//Focus Anchor in URL
var url = document.location.toString();
if (url.match('#')) {
	$('.anchor a[href="#'+url.split('#')[1]+'"]').tab('show');
}

//Focus Modal
//$('#topoModalCenter').on('shown.bs.modal', function () {
//	  $('#id').trigger('focus')
//	})