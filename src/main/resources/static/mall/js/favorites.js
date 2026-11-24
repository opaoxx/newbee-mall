$(document).on('click', '[data-favorite-url]', function () {
    const button = $(this);
    const isListAction = button.is('[data-favorite-remove]');
    const isFavorited = button.attr('aria-pressed') === 'true';
    button.prop('disabled', true);

    $.ajax({
        url: button.attr('data-favorite-url'),
        method: isListAction || isFavorited ? 'DELETE' : 'PUT',
        dataType: 'json'
    }).done(function (result) {
        if (result.resultCode !== 200) {
            Swal.fire({text: result.message || '收藏操作失败', icon: 'error'});
            return;
        }
        if (isListAction) {
            window.location.reload();
            return;
        }
        const favorited = result.data.favorited;
        button.attr('aria-pressed', String(favorited));
        button.text(favorited ? '取消收藏' : '收藏');
    }).fail(function (response) {
        if (response.status === 401) {
            window.location.assign(button.attr('data-login-url'));
            return;
        }
        const message = response.responseJSON && response.responseJSON.message;
        Swal.fire({text: message || '收藏操作失败，请稍后重试', icon: 'error'});
    }).always(function () {
        button.prop('disabled', false);
    });
});
