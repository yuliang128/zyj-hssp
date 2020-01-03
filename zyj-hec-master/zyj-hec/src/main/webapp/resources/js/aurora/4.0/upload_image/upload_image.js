var vm = new Vue({
    el : '#main',
    data : {
        baseUrl : window.baseUrl,
        images : [],
        file_img : []
    },
    methods : {
        chooseImage : function(event) {
            if (!$('#image-choose').val().match(/.jpg|.jpeg|.gif|.png|.bpm/i)) {
                ReAlert('提示', '上传的图片格式不正确，请重新选择!', function() {
                });
                return;
            }
            for (var i = 0; i < event.currentTarget.files.length; i++) {
                this.file_img.push(event.currentTarget.files[i]);
                var reader = new FileReader();
                reader.readAsDataURL(event.currentTarget.files[i]);
                $(reader).on('load', function(e) {
                    vm.images.push({
                        src : this.result
                    });
                });
            }
        },
        uploadImage : function() {
            if (vm.file_img.length == 0) {
                ReAlert('提示', '请先选择您要上传的图片！', function() {
                });
                return;
            }
            var formData = new FormData($('#uploadImageForm')[0]);
            for (i = 0; i < this.file_img.length; i++) {
                formData.append("images[" + i + "]", this.file_img[i]);
            }
            $.ajax({
                url : vm.baseUrl + '/atm_upload.svc?multi_upload=Y',
                type : 'POST',
                data : formData,
                async : false,
                cache : false,
                contentType : false,
                processData : false,
                success : function(returndata) {
                    var resp = JSON.parse(returndata);
                    if (resp.success) {
                        vm.images.splice(0, vm.images.length);
                        vm.file_img = [];
                        ReAlert('提示', '上传成功!', function() {
                        });
                    }
                },
                error : function(returndata) {
                    ReAlert('提示', '请求失败！', function() {
                    });
                }
            });
        }
    }
});

// 自定义弹出框
function ReAlert(title, content, fun) {
    $('#alertModal .modal-title').text(title);
    $('#alertModal .modal-body p').text(content);
    $('#alertModal').modal('show');
    $('#alertModal .sureBtn').off('click').click(function() {
        fun();
    })
};