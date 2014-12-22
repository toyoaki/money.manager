var moneyTransaction = moneyTransaction || {};  

moneyTransaction.nextInt = function(){
	var i=0;
	function component(){}
	component.next = function(){return i++;}
	return component;
}

moneyTransaction.moneyTransactionImport = function(){

	var MONEY_TRANSACTION_CONTAINER_ID = "moneyTransactionsContainer";
	var MONEY_TRANSACTION_TABLE_ID = "moneyTransactionsTable";
	
	var MONEY_TRANSACTION_CONTAINER_SELECTOR = "#" + MONEY_TRANSACTION_CONTAINER_ID;
	var MONEY_TRANSACTION_TABLE_SELECTOR = "#"+ MONEY_TRANSACTION_TABLE_ID;
	
	var FILE_UPLOAD_URL = "/money.manager/money_transaction_import/upload_file";
	var FIND_CATEGORIES_URL = "/money.manager/category/find_all";
	
	var nextInt;
	var allCategories;
	
	function component(){}
	
	component.initialize = function(){
		nextInt = moneyTransaction.nextInt();
		component.moneyTransactionTable = moneyTransactionTable().initialize();
		component.fileUpload = fileUpload().initialize(component.moneyTransactionTable);    
		loadAllCategories();
		
		return component;
	}
	
	function loadAllCategories(){
		$.ajax({url: FIND_CATEGORIES_URL, async: false})
			.done(function(categories){allCategories = categories;});		
	}

	function moneyTransactionTable(){
		function component(){}
		
		component.initialize = function(){
			return component;
		}
		
		component.render = function(moneyTransactions){
			var container = d3.select(MONEY_TRANSACTION_CONTAINER_SELECTOR);
			container.select("table").remove();
			
			var table = container.append("table").classed("table", true).attr("id", MONEY_TRANSACTION_TABLE_ID);
			var theadRow = table.append("thead").append("tr");
			theadRow.append("th").text("Data");
			theadRow.append("th").text("Valor");
			theadRow.append("th").text("Descrição");
			theadRow.append("th").text("Categoria(s)");
			theadRow.append("th");
			var tbody = table.append("tbody");
			
			for(var i = 0; i < moneyTransactions.length; i++){
				var moneyTransaction = moneyTransactions[i];
				var tr = tbody.append("tr");
				tr.append("td").text(moneyTransaction.date)
				tr.append("td").text(moneyTransaction.value)
				tr.append("td").text(moneyTransaction.description);
				
				var categories = moneyTransaction.categories;
				
				var selectedCategoriesDiv = tr.append("td").append("div").classed("categories-column", true);
				addSelectedCategories(selectedCategoriesDiv, categories);
				
				var selectionComponentTd = tr.append("td");
				addCategorySelectionComponent(selectionComponentTd);
			}
			return component;
		}
		
		function addSelectedCategories(parent, categories){
			categories.forEach(function(category, index, array){
				addSelectedCategory(parent, category);	
			})
		}
		
		function addSelectedCategory(parent, category){
			var labelId = "closeable-label-" + nextInt.next();
			var style = {"color": category.fontColor, "background-color": category.backgroundColor};
			
			var labelDiv = parent.append("div").classed("closeable-label-container", true).attr("id", labelId);
			labelDiv.data([{'category': category}]);
			labelDiv.append("div").classed("closeable-label-label", true).style(style).text(category.name);
			var closeLabel = labelDiv.append("div").classed("closeable-label-close", true).style(style)
				.append("span").classed("glyphicon glyphicon-remove", true);
			closeLabel.on("click", function(data, index){
				var label = $(labelDiv[0][0]);
				label.fadeOut(400, function(){label.remove()});});
		}
		
		function addCategorySelectionComponent(parent){
			function addCategories(){
				var categoryParent = d3.select($(parent[0][0]).parents("tr").find(".categories-column")[0]);
				function addCategory(parent, category){
					parent.append("li").append("div")
						.classed("categories-dropdown-label", true)
						.style({'background-color': category.backgroundColor, 'color': category.fontColor})
						.text(category.name)
						.on("click", function(data, index){
							addSelectedCategory(categoryParent, category);
						});
				}
				
				var dropdown = $(ul[0][0]);
				dropdown.children().remove();
				
				var selectedCategoriesIds = getSelectedCategoriesIds(dropdown.parents("tr")[0]);
				
				allCategories.forEach(function(category, index, array){
					if(selectedCategoriesIds.indexOf(category.id) == -1){
						addCategory(ul, category);
					}});
				
				ul.append("li").attr("role", "presentation").classed("divider categories-dropdown-separator", true);
				ul.append("li").attr("role", "presentation").append("a").text("Criar");
			}
			
			var div = parent.append("div").classed("dropdown categories-dropdown-container", true)
				.on("click", function(data, index){addCategories();})
			div.append("button").attr({"type": "button", "data-toggle": "dropdown"}).classed("btn btn-default", true)
				.append("span").classed("glyphicon glyphicon-tag", true);
			var ul = div.append("ul").classed("dropdown-menu dropdown-menu-right categories-dropdown", true);
			
			addCategories();
		}
		
		function getSelectedCategoriesIds(row){
			return getSelectedCategories(row).map(function(category){return category.id;});
		}
		
		function getSelectedCategories(row){
			var selectedCategories = [];
			$.each($(row).find(".closeable-label-container"), function(index, selectedCategory){
				var selectedCategory = d3.select(selectedCategory).data()[0].category;
				selectedCategories.push(selectedCategory);
			});
			return selectedCategories;
		}		
		
		component.getMoneyTransactions = function(){
			var rows = d3.select("#" + MONEY_TRANSACTION_TABLE_ID).select("tbody").selectAll("tr")[0];
			$.each(rows, function(index, row){
				var category = getSelectedCategories(row);
				console.info(index + ": " + getSelectedCategories(row)[0].name);
			});
			return component;
		}
					
		return component;
	}
	
	function fileUpload(){
		var moneyTransactionTable;
		
		function component(){}
		
		component.initialize = function(_moneyTransactionTable){
			moneyTransactionTable = _moneyTransactionTable;
			var uploadButton = $('.btn-file :file');
			uploadButton.on('change', 
				function(){
					var input = $(this),
						numFiles = input.get(0).files ? input.get(0).files.length : 1,
						label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
					input.trigger('fileselect', [numFiles, label]);
				}
			);
			uploadButton.on('fileselect', 
				function(event, numFiles, label) {
			        var input = $(this).parents('.input-group').find(':text'),
			            log = numFiles > 1 ? numFiles + ' files selected' : label;
			        
			        if( input.length ) {
			            input.val(log);
			        } else {
			            if( log ) alert(log);
			        }
		    	}
			);	
			return component;
		}
		
		component.upload = function(){
			var formData = new FormData($("#fileForm")[0]);
			$.ajax({
				url: FILE_UPLOAD_URL,
				type: 'POST',
				data: formData,
				dataType: "json",
				async: false, cache: false, contentType: false, processData: false,
				success: function (returndata) {moneyTransactionTable.render(returndata);},
				error: function(){alert("Erro inesperado ao recuperar dados!");}
			});
			return component;
		};	
		
		return component;
	}		
	
	return component;
};

var page;

$(document).ready(function() {
	page = moneyTransaction.moneyTransactionImport().initialize();
});
