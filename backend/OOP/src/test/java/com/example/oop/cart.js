const checkboxes = document.querySelectorAll('.product-check');
const totalPriceElement = document.getElementById('total-price');

function updateTotal() {
  let total = 0;
  document.querySelectorAll('.product-check').forEach(cb => {
    if (cb.checked) {
      total += parseInt(cb.getAttribute('data-price'));
    }
  });
  totalPriceElement.textContent = total.toLocaleString('vi-VN') + '₫';
}

// Gắn sự kiện thay đổi cho checkbox
checkboxes.forEach(checkbox => {
  checkbox.addEventListener('change', updateTotal);
});

// Gắn sự kiện xóa cho nút remove
document.querySelectorAll('.remove').forEach(button => {
  button.addEventListener('click', function () {
    const productItem = this.closest('.product-item');
    productItem.remove();
    updateTotal(); // cập nhật lại tổng giá
  });
});
