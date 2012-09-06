//package controllers;
//
//import models.Card;
//
///**
// * Created with IntelliJ IDEA.
// * User: Team w&w
// * Date: 8/29/12
// * Time: 7:23 PM
// * To change this template use File | Settings | File Templates.
// */
//
//public class CheatAlgorithm {
//        Card player1 ;   // carta elejida por player1
//        Card player2 ;   // carta elejida por player2
//        Integer NumberOfCheatsP1 = 0;    // numero de trampas del player1 hasta ahora
//        Integer NumberOfCheatsP2 = 0;    // numero de trampas del player2 hasta ahora
//
//
//        // Todas las preguntas estan tagueadas con un sistema de ID'S, y de esta forma podemos identificar que significa cada pregunta sint ener que analizarla sintacticamente.
//        // Las respuestas son siempre boolean, SI O NO.
//        public CheatAlgorithm(Card user1, Card user2){
//
//            this.player1=user1;
//            this.player2=user2;
//
//        }
//
//        public String getData(Integer id){
//            return null;
//        }
//
//        public void player1Pregunta (String idPregunta, boolean respuesta){
//
//            // tengo q sacar la info de la carta del player2
//
//                switch (idPregunta) {
//                    case ID1:     // Pregunto por color de pelo marron.
//                        String TrueAnswer = player2.getHairColor();        // saco el valor real del pelo de la carta elejida por el player2
//                        if (TrueAnswer.equals("brawn")){                   // me fijo si es marron, como me pregunto el player 1
//                                if (respuesta == false){                  // si es marron, el player1 deveria responder SI , ENTONCES SI RESPONDE NO, le sumo mentira al player1.
//                                NumberOfCheatsP1 +=1;
//                                }
//                        }
//                        else {
//
//                            if (respuesta == true){                    // si no es marron y el player1 respondio SI ES MARRON, entonces le sumo una mentira apl player1.
//                                NumberOfCheatsP1 +=1;
//                            }
//
//                        }
//                        break;
//                    case ID2:     // Pregunto por color de pelo amarillo.
//                        String TrueAnswer = player2.getHairColor();
//                        if (TrueAnswer.equals("yellow")){
//                            if (respuesta == false){
//                                NumberOfCheatsP1 +=1;
//                            }
//                        }
//                        else {
//
//                            if (respuesta == true){
//                                NumberOfCheatsP1 +=1;
//                            }
//
//                        }
//                        break;
//                    case ID3:     // Pregunto por color de pelo NEGRO.
//                        String TrueAnswer = player2.getHairColor();
//                        if (TrueAnswer.equals("black")){
//                            if (respuesta == false){
//                                NumberOfCheatsP1 +=1;
//                            }
//                        }
//                        else {
//
//                            if (respuesta == true){
//                                NumberOfCheatsP1 +=1;
//                            }
//
//                        }
//                        break;
//                    case ID4:     // Pregunto por color de pelo blanco.
//                        String TrueAnswer = player2.getHairColor();
//                        if (TrueAnswer.equals("white")){
//                            if (respuesta == false){
//                                NumberOfCheatsP1 +=1;
//                            }
//                        }
//                        else {
//
//                            if (respuesta == true){
//                                NumberOfCheatsP1 +=1;
//                            }
//
//                        }
//                        break;
//                    case ID5:     // Pregunto por color de pelo naranja.
//                        String TrueAnswer = player2.getHairColor();
//                        if (TrueAnswer.equals("orange")){
//                            if (respuesta == false){
//                                NumberOfCheatsP1 +=1;
//                            }
//                        }
//                        else {
//
//                            if (respuesta == true){
//                                NumberOfCheatsP1 +=1;
//                            }
//
//                        }
//                    break;
//                    // aca temrinan los casos del color de pelo. Quedan 7 casos mas que son el resto de los atributos como si tiene barba, si tiene sombraro, etc.
//                }
//
//            }
//
//
//
//
//        public void player2Pregunta (Integer idPregunta,boolean respuesta){
//
//
//            // tengo q sacar la info de la carta del player1
//
//                switch (idPregunta) {
//                    case ID1:     // Pregunto por color de pelo marron.
//                        String TrueAnswer = player1.getHairColor();        // saco el valor real del pelo de la carta elejida por el player2
//                        if (TrueAnswer.equals("brawn")){                   // me fijo si es marron, como me pregunto el player 1
//                            if (respuesta == false){                  // si es marron, el player1 deveria responder SI , ENTONCES SI RESPONDE NO, le sumo mentira al player1.
//                                NumberOfCheatsP2 +=1;
//                            }
//                        }
//                        else {
//
//                            if (respuesta == true){                    // si no es marron y el player1 respondio SI ES MARRON, entonces le sumo una mentira apl player1.
//                                NumberOfCheatsP2 +=1;
//                            }
//
//                        }
//                        break;
//                    case ID2:     // Pregunto por color de pelo amarillo.
//                        String TrueAnswer = player1.getHairColor();
//                        if (TrueAnswer.equals("yellow")){
//                            if (respuesta == false){
//                                NumberOfCheatsP2 +=1;
//                            }
//                        }
//                        else {
//
//                            if (respuesta == true){
//                                NumberOfCheatsP2 +=1;
//                            }
//
//                        }
//                        break;
//                    case ID3:     // Pregunto por color de pelo NEGRO.
//                        String TrueAnswer = player1.getHairColor();
//                        if (TrueAnswer.equals("black")){
//                            if (respuesta == false){
//                                NumberOfCheatsP2 +=1;
//                            }
//                        }
//                        else {
//
//                            if (respuesta == true){
//                                NumberOfCheatsP2 +=1;
//                            }
//
//                        }
//                        break;
//                    case ID4:     // Pregunto por color de pelo blanco.
//                        String TrueAnswer = player1.getHairColor();
//                        if (TrueAnswer.equals("white")){
//                            if (respuesta == false){
//                                NumberOfCheatsP2 +=1;
//                            }
//                        }
//                        else {
//
//                            if (respuesta == true){
//                                NumberOfCheatsP2 +=1;
//                            }
//
//                        }
//                        break;
//                    case ID5:     // Pregunto por color de pelo naranja.
//                        String TrueAnswer = player1.getHairColor();
//                        if (TrueAnswer.equals("orange")){
//                            if (respuesta == false){
//                                NumberOfCheatsP2 +=1;
//                            }
//                        }
//                        else {
//
//                            if (respuesta == true){
//                                NumberOfCheatsP2 +=1;
//                            }
//
//                        }
//                        break;
//                    // aca temrinan los casos del color de pelo. Quedan 7 casos mas que son el resto de los atributos como si tiene barba, si tiene sombraro, etc.
//                }
//
//            }
//}
