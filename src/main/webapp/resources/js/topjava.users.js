const ajaxUrl = "ajax/admin/users/";
let datatableApi;

function enabled(checkbox, id){
    const enabled=checkbox.is(":checked");
    $.ajax({
        url: ajaxUrl+id,
        type: "POST",
        data: "enabled="+enabled
    }).done(
        checkbox.closest("tr").attr("is_user_enabled", enabled)
    );





}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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