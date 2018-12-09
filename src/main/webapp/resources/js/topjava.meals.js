const ajaxUrl = "ajax/profile/meals/";
let datatableApi;


function updateTableWithFilter() {
    $.ajax({
        url: ajaxUrl + "filter",
        type: "GET",
        data:$("#filter").serialize()
    }).done(function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function abortFilter(){
    $("#filter")[0].reset();
    updateTable();

}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});
