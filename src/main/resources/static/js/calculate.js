$("#btnSubmit").click(function (event) {
    event.preventDefault();
    var form = $('#formCalculate').serialize();
    var sumPercent;
    var loanAmount;
    var totalAmountPayments;
    $("#btnSubmit").prop("disabled", true);
    $.ajax({
        type: "GET",
        url: "/calculation",
        contentType: "application/json",
        data: form,
        success: function (data) {
            console.log(data);
            sumPercent = data.page.sumPercent;
            loanAmount = data.page.loanAmount;
            totalAmountPayments = data.page.totalAmountPayments;
            document.getElementById("container").innerHTML = "";
            document.getElementById("messageError").innerHTML = "";
            $('#btnSubmit').attr('disabled', false);
            var container = $("#container");
            var content = `<table><caption>График платежей</caption><thead><tr><th>Номер платежа</th>
            <th>Дата платежа</th><th>Сумма процентов</th><th>Сумма основного долга</th><th>Сумма платежа</th>
            <th>Остаток основного долга</th></tr></thead><tbody>`;
            data.page.content.forEach(function (item) {
                var date = new Date(`${item.paymentDate}`);
                options = {
                    year: 'numeric', month: 'numeric', day: 'numeric',
                };
                var dateFormat = new Intl.DateTimeFormat('en-GB', options);
                content +=
                    `<tr>
                        <td> ${item.paymentNumber} </td>
                        <td> ${dateFormat.format(date)} </td>
                        <td> ${item.amountInterest} </td>
                        <td> ${item.amountPrincipal} </td>
                        <td> ${item.paymentAmount} </td>
                        <td> ${item.principalBalance} </td>
                     </tr>`;
            });
            content += `<tr>
                            <td><b>Итого</b></td>
                            <td></td>
                            <td><b>${sumPercent}</b></td>
                            <td><b>${loanAmount}</b></td>
                            <td><b>${totalAmountPayments}</b></td>
                            <td></td>
                        </tr>
                       </tbody></table>`;

            container.append(content);
        },
        error: function (xhr, status, error) {
            document.getElementById("container").innerHTML = "";
            document.getElementById("messageError").innerHTML = "";
            var err = JSON.parse(xhr.responseText);
            $('#btnSubmit').attr('disabled', false);
            var message = $("#messageError");
            message.append(err.messageList);
        }
    })
});

function checkParams() {
    var loanAmount = $('#loanAmount').val();
    var loanPeriod = $('#loanPeriod').val();
    var interestRate = $('#interestRate').val();

    if (loanAmount.length !== 0 && loanPeriod.length !== 0 && interestRate.length !== 0) {
        $('#btnSubmit').removeAttr('disabled');
    } else {
        $('#btnSubmit').attr('disabled', 'disabled');
    }
}
