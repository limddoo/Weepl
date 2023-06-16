$(document).ready(function() {
  const slides = $('#main_slide .Vimg');
  const prevButton = $('#prev_slide');
  const nextButton = $('#next_slide');
  const playPauseButton = $('#play_pause_button');
  const playPauseImage = $('#play_pause_image');

  let currentSlide = 0;
  let isSlideShowRunning = true;
  let slideInterval = setInterval(() => {
    if (isSlideShowRunning) {
      slideTo('next');
    }
  }, 5000);

  function showSlide(n) {
    slides.each(function(index) {
      if (index === n) {
        $(this).addClass('active').show();
      } else {
        $(this).removeClass('active').hide();
      }
    });
    currentSlide = n;
    localStorage.setItem('currentSlideIndex', currentSlide.toString());
  }

  function toggleSlideShow() {
    if (isSlideShowRunning) {
      clearInterval(slideInterval);
      playPauseImage.attr('src', '/img/main/play.png');
      isSlideShowRunning = false;
    } else {
      slideInterval = setInterval(() => {
        showSlide((currentSlide + 1) % slides.length);
      }, 5000);
      playPauseImage.attr('src', '/img/main/stop.png');
      isSlideShowRunning = true;
    }
  }

  function slideTo(direction) {
    slides.eq(currentSlide).removeClass('active').hide();

    if (direction === 'prev') {
      currentSlide = (currentSlide - 1 + slides.length) % slides.length;
    } else {
      currentSlide = (currentSlide + 1) % slides.length;
    }

    slides.eq(currentSlide).addClass('active').show();
  }

  function storeCurrentSlideIndex() {
    localStorage.setItem('currentSlideIndex', currentSlide.toString());
  }

  // 슬라이드 숨김 함수
  function hideSlides() {
    slides.each(function(index) {
      if (index !== 0) { // 슬라이드 인덱스가 0이 아닌 경우 hide() 메소드를 사용하여 숨김
        $(this).hide();
      }
    });
  }

  // 페이지 로드 시 슬라이드 초기화
  $(window).on('load', function() {
    hideSlides(); // 초기에 모든 슬라이드 숨김
    showSlide(currentSlide);
    adjustSlideContainerSize(); // 슬라이드 컨테이너 크기 조정
  });

  prevButton.on('click', function() {
    slideTo('prev');
    storeCurrentSlideIndex();
  });

  nextButton.on('click', function() {
    slideTo('next');
    storeCurrentSlideIndex();
  });

  playPauseButton.on('click', function() {
    toggleSlideShow();
  });

  function adjustSlideContainerSize() {
    const slideContainer = $('#main_slide');
    slideContainer.height(slides.eq(currentSlide).height());
  }
});
