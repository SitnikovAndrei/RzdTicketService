$('.myTable').DataTable( {
    ajax: { "url": 'tickets/all', "dataSrc": ""},
    columns: [{ data: "id"}, { data: "brand"}, { data: "number"},{ data: "cityFrom"},{ data: "cityTo"}, { data: "dateFrom"}, { data: "dateTo"}, { data: "price"}]
} );