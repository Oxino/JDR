<?php
//Tout ce qui sera dans le document sera lu par le serveur il faut donc l'optimiser.
//echo''; Ecris quelque chose
//$variable; Crée une variable
session_start(); //Elle doit être lencé avant n'importe quel bout d'HTML, crée un espace memoire sur le serveur
$_SESSION['coucou']='Tim';//Element globale

//__DIR__ emplacement du fichier actuel 
define('__SITE_DIR__',__DIR__); //Site dir deviens la racine de tous le projet peut importe le dossier
define('__FUNCTION_DIR__', __SITE_DIR__.'/functions'); //__FUNCTIOND_IR__ devien un racourci pour __SITE_DIR__.'/function'
define('__VUES_DIR__', __SITE_DIR__.'/vues');
define('site_url','adresse du site');


include __FUNCTION_DIR__.'/function.php';

$page = GET_('page'); //On appelle la fonction GET qui est dans functions
//echo $_SESSION['coucou']; On teste que la variable reste dans la session

switch($page){
    //On le met a false comme quand il n'y a rien dans l'url on est redirigé vers la page home et il n'y a qu'une page home
    case false:
        include __VUES_DIR__.'/header.php';
        include __VUES_DIR__.'/home.php';
        include __VUES_DIR__.'/footer.php';
    break;
    case 'contact':
        include __VUES_DIR__.'/header.php';
        include __VUES_DIR__.'/contact.php';
        include __VUES_DIR__.'/footer.php';
    break'';
    case '404':
        include __VUES_DIR__.'/header.php';
        include __VUES_DIR__.'/404.php';
        include __VUES_DIR__.'/footer.php';
    break'';
    default:
        header('location: '.site_url.'/?404.php'); //Redirige vers la page 404 le header location redirige en ecrivant dans l'url, les point servent a ajouter dans la string
        die(); //Pour laisser le temps de faire le header location, on peut aussi mettre exit()
    break;
    
}

//include ou require pour inclur un document php include l programe continu s'il trouve pas et requier il s'arrete. 
//__DIR__ racine du serveur 