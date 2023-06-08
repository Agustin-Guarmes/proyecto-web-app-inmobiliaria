$('.custom-table').DataTable({
    responsive: true,
    autoWidth: false,
    "language": {
        "lengthMenu": "Mostrar _MENU_ registros por página",
        "zeroRecords": "Nada encontrado - disculpe",
        "info": "Página _PAGE_ de _PAGES_",
        "infoEmpty": "No hay registros disponibles",
        "infoFiltered": "(filtrado de _MAX_ registros totales)",
        "search": "Buscar:",
        "paginate" : {
            "previous": "Anterior",
            "next": "Siguiente",
        }
    },
});
