document.addEventListener('DOMContentLoaded', function () {
    const popupOverlay = document.getElementById('image-popup');
    const qrButton = document.getElementById('select2');

    if (qrButton && popupOverlay) {
        qrButton.addEventListener('click', function () {
            popupOverlay.style.display = 'flex';
        });
    }
});
