# Projeto para treinamento Spring

Objetivo desse projeto é treinar e aperfeiçoar as habilidades em Spring
com um CRUD. Enunciado desta atividade:

SISTEMA PARTIDA DE FUTEBOL

Lista de Partidas de Futebol
Objetivo: desenvolver uma API para salvar partidas de futebol.

Requisitos:
- Java 17 ou 21
- SpringBoot
- Persistir os dados em um banco de dados relacional (MySQL, Postgres, SQLite, H2, HSQLDB, etc.)

Operações básicas:
- A aplicação deverá permitir...
- Cadastrar uma partida, contendo no mínimo o nome dos clubes, o resultado da partida, a data e a hora da partida e o nome do estádio;
- Atualizar os dados de uma partida;
- Remover uma partida do cadastro;

Buscas:
A aplicação deverá permitir a busca por...
- Partidas que terminaram em uma goleada (3 ou mais gols de diferença para um dos clubes);
- Partidas que terminaram sem gols para nenhum dos clubes;
- Todas as partidas de um clube específico, podendo filtrar as partidas onde este clube atuou como mandante ou como visitante;
- Todas as partidas de um estádio específico;

Validações:
A aplicação não deverá permitir o cadastro ou a atualização...
- De uma partida antes das 8h ou após às 22h;
- De mais de uma partida em um mesmo estádio no mesmo dia;
- De mais de uma partida de um mesmo clube com menos de dois dias de intervalo;
- De uma partida sem conter o nome dos clubes, a data e a hora da partida e o nome do estádio;
- De uma partida com a data e a hora da partida no futuro;
- De uma partida sem conter o resultado, ou com valores negativos no resultado;

