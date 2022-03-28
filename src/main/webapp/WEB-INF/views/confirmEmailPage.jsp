<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>confirm email</title>

    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />

    <!-- Bootstrap CSS -->
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
            integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
            crossorigin="anonymous"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/component/css/style.css" />

</head>
<body>

<div class="container">

    <div class="center">
        <div style="background-color: white; height: 300px; width: 500px; border-radius: 15px;" >

            <div class=" p-5 justify-content-center m-4">

                <form:form action="/confirmEmail">
                    <p>we sent the verification code to your email <br/> please check your email and enter the code.</p>
                    <br/>
                    <input type="number" name="code" class="form-control"/>
                    <br/>
                    <input type="submit" value="verify" id="verify" class="btn btn-primary" style="background-color: #4B49AC"/>
                    <p id="message"></p>
                </form:form>
            </div>
        </div>
    </div>

</div>

<script
        src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
        crossorigin="anonymous"
></script>
</body>
</html>
