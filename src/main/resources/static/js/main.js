
$(document).ready(function () {

    /* Order Items */
    $("#order-form").on("submit", function (event) {
        event.preventDefault();
    })

    function calculateNetAmount() {
        let grossAmount = 0;
        let vatValue = parseFloat($("#vatValue").val());
        let discount = parseFloat($("#discount").val());

        $(".amount").each(function (i, obj) {
            grossAmount += parseFloat(obj.value);
        });

        $("#grossAmount").val(grossAmount.toFixed(2));
        $("#vat").val((grossAmount * (vatValue / 100)).toFixed(2));
        $("#netAmount").val((grossAmount * (1 + (vatValue / 100)) * (1 - (discount / 100))).toFixed(2));
    }

    /* Custom Valication */
    $("#customerName, #customerAddress, #customerPhone").each(function (i, input) {
        $(input).on("input", function () {
            if ($(this).val() === "" || $(this).val().length < 1) {
                $(this).removeClass("is-valid")
                $(this).addClass("is-invalid");
            } else {
                $(this).removeClass("is-invalid")
                $(this).addClass("is-valid");
            }
        });
    });

    $("#create-order").on("click", function (event) {
        event.preventDefault();

        let productQtys = [];

        $(".products").each(function (i, obj) {
            productQtys.push({
                productId: $(this).val(),
                qty: $(this).parent().parent().find(".qty").val()
            });
        });

        let order = {
            customerName: $("#customerName").val(),
            customerAddress: $("#customerAddress").val(),
            customerPhone: $("#customerPhone").val(),
            productQtys,
            grossAmount: $("#grossAmount").val(),
            vatCharge: $("#vat").val(),
            vatAmount: $("#vatValue").val(),
            discount: $("#discount").val(),
            netAmount: $("#netAmount").val()
        };

        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");

        $.ajaxSetup({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        });

        $.ajax({
            url: "http://localhost:8080/api/orders",
            type: "POST",
            data: JSON.stringify(order),
            contentType: "application/json",
            success: function (data, status, xhr) {
                window.location.href = "http://localhost:8080/orders?success";
            },
            error: function (data, status, xhr) {
                window.location.href = "http://localhost:8080/orders/add?error";
            }
        });

    });

    $(".add-item").click(function (event) {
        event.preventDefault();

        $("#items-table tbody").append($("<tr></tr>")
                .append($("<td></td>")
                        .append(function () {
                            let select = $("<select></select>").addClass("browser-default custom-select products")
                            select.append("<option>---</option>")

                            $.getJSON("http://localhost:8080/api/products", function (products) {
                                $.each(products, function (key, product) {
                                    select.append($("<option></option>").attr({value: product.id}).append(product.name));
                                });
                            });

                            return select;
                        })
                        )
                .append($("<td></td>")
                        .append($("<input/>").attr({type: "text", value: 0}).addClass("form-control qty"))
                        )
                .append($("<td></td>")
                        .append($("<input/>").attr({type: "text", value: 0, disabled: true}).addClass("form-control price"))
                        )
                .append($("<td></td>")
                        .append($("<input/>").attr({type: "text", value: 0, disabled: true}).addClass("form-control amount"))
                        )
                .append($("<td></td>")
                        .append($("<button></button>").attr({type: "button"}).addClass("btn btn-sm btn-light-blue remove-item")
                                .append($("<i></i>").addClass("fas fa-times"))
                                )
                        )
                );

        $(".remove-item").click(function (event) {
            event.preventDefault();
            $(this).parent().parent().remove();
            calculateNetAmount();
        });

        $(".products").on("change", function (event) {
            event.preventDefault();
            let parent = $(this).parent().parent();
            $.getJSON("http://localhost:8080/api/products/" + $(this).val(), function (product) {
                let qty = parseFloat(parent.find(".qty").val());

                parent.find(".price").val(product.price);
                parent.find(".amount").val(product.price * qty);
            });
            calculateNetAmount();
        });

        $(".qty").on("change", function () {
            let price = $(this).parent().parent().find(".price").val();
            let amount = $(this).parent().parent().find(".amount");
            amount.val(parseFloat(price) * parseFloat($(this).val()));
            calculateNetAmount();
        });

        $("#discount").on("change", function () {
            calculateNetAmount();
        });
    });

    /* Datatables */
    $('.dt').DataTable({"pagingType": "full"});
    $('.dataTables_length').addClass('bs-select');

    /*
     * Modal - Delete
     */
    /* Information Injection */
    $(".btn-delete").click(function (event) {
        $("#centralModalInfoDelete").attr("data-path", $(this).attr("href"));
        $("#centralModalInfoDelete").attr("data-redirect", $(this).attr("data-redirect"))
        $("#centralModalInfoDelete .modal-message").text($(this).attr("data-message"));
        event.preventDefault();
    });

    // show.bs.modal, shown.bs.modal, hide.bs.modal, hidden.bs.modal
    /* Clear */
    $("#centralModalInfoDelete").on('hide.bs.modal', function () {
        $(this).attr("data-path", "");
        $(this).attr("data-redirect", "");
        $(this).children(".modal-message").text("");
    });

    /* Server delete request */
    $("#centralModalInfoDeleteBtnYes").click(function () {
        let url = "http://localhost:8080" + $("#centralModalInfoDelete").attr("data-path");
        let redirect = "http://localhost:8080" + $("#centralModalInfoDelete").attr("data-redirect");

        $.ajax({
            url: url,
            beforeSend: function (xhr) {
                xhr.overrideMimeType("text/plain; charset=x-user-defined");
            }
        }).done(function (data) {
            if (console && console.log) {
                console.log("Sample of data:", data.slice(0, 100));
            }

            window.location.href = redirect;
        });
    });

    /* Charts */
    // Line
    var ctx = document.getElementById("myChart").getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
            datasets: [{
                    label: '# of Paid Orders',
                    data: [12, 19, 3, 5, 2, 3, 15, 11, 33, 25, 12, 3],
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(54, 162, 235, 0.2)'
                    ],
                    borderColor: [
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(54, 162, 235, 1)'
                    ],
                    borderWidth: 1
                }]
        },
        options: {
            scales: {
                yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
            }
        }
    });

});