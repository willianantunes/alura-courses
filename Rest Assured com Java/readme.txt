Caso n�o tenha fa�a download do Apache Ant:

https://ant.apache.org/bindownload.cgi
http://ant.apache.org/manual/install.html#getting

Se ele estiver em seu environment e funcional, execute o seguinte comando para rodar o aplicativo de leil�es:

> ant jetty.run

Ap�s terminar o processo acessar localhost:8080
� uma solu��o similar a usada no curso de Selenium, a diferen�a � que tem servi�os web. Para testar acesse por exemplo localhost:8080/usuarios?_format=xml ou localhost:8080/usuarios?_format=json