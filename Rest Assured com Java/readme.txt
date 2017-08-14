Caso não tenha faça download do Apache Ant:

https://ant.apache.org/bindownload.cgi
http://ant.apache.org/manual/install.html#getting

Se ele estiver em seu environment e funcional, execute o seguinte comando para rodar o aplicativo de leilões:

> ant jetty.run

Após terminar o processo acessar localhost:8080
É uma solução similar a usada no curso de Selenium, a diferença é que tem serviços web. Para testar acesse por exemplo localhost:8080/usuarios?_format=xml ou localhost:8080/usuarios?_format=json