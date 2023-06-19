$(document).ready(function() {
  const slides = $('#main_slide .Vimg');
  const prevButton = $('#prev_slide');
  const nextButton = $('#next_slide');
  const playPauseButton = $('#play_pause_button');
  const playPauseImage = $('#play_pause_image');
  const video = $('#popupzone_video_0');

  let currentSlide = 0;
  let isSlideShowRunning = true;
  let slideInterval;

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
      stopVideo(); // 동영상 재생 중지 함수 호출
    } else {
      slideInterval = setInterval(() => {
        if (!isVideoPlaying()) { // 동영상이 재생 중이 아닌 경우에만 슬라이드 전환
          showSlide((currentSlide + 1) % slides.length);
        }
      }, 5000);
      playPauseImage.attr('src', '/img/main/stop.png');
      isSlideShowRunning = true;
      startSlideShow(); // 슬라이드쇼 다시 시작
      playVideo(); // 동영상 재생 함수 호출
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

  function hideSlides() {
    slides.each(function(index) {
      if (index !== 0) {
        $(this).hide();
      }
    });
  }

  $(window).on('load', function() {
    showSlide(currentSlide);
    adjustSlideContainerSize();
    startSlideShow(); // 슬라이드쇼 시작
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

  video.on('play', function() {
    if (isSlideShowRunning) {
      clearInterval(slideInterval);
      isSlideShowRunning = false;
      playPauseImage.attr('src', '/img/main/play.png');
    }
  });

  video.on('ended', function() {
    if (!isSlideShowRunning) {
      slideInterval = setInterval(() => {
        if (!isVideoPlaying()) {
          showSlide((currentSlide + 1) % slides.length);
        }
      }, 5000);
      isSlideShowRunning = true;
      startSlideShow();
      playPauseImage.attr('src', '/img/main/stop.png');
    }
  });

  function adjustSlideContainerSize() {
    const slideContainer = $('#main_slide');
    slideContainer.height(slides.eq(currentSlide).height());
  }

  function startSlideShow() {
    slideInterval = setInterval(() => {
      if (isSlideShowRunning) {
        slideTo('next');
      }
    }, 5000);
  }
});
