/*
 *  Diretiva para o Botão do Menu.
 */
nutrIFApp.run(['Carousel', (Carousel) => {
    Carousel.setOptions({
        arrows: true,
        autoplay: false,
        autoplaySpeed: 3000,
        cssEase: 'ease',
        dots: false,

        easing: 'linear',
        fade: false,
        infinite: true,
        initialSlide: 0,

        slidesToShow: 1,
        slidesToScroll: 1,
        speed: 500,
    });
}]);