const summary_title_edit = document.querySelector('.summary-title .summary-text');
const submit_title = document.querySelector('.summary-submit')
const save_title = document.querySelector('#save-title');
const cancel_title = document.querySelector('#cancel-title');
let original_title;

submit_title.onclick = () => {
    original_title = summary_title_edit.value;
    summary_title_edit.readOnly = false;
    submit_title.style.setProperty('display', 'none');
    save_title.style.removeProperty('display');
    cancel_title.style.removeProperty('display');

    const resetButton = () => {
        summary_title_edit.readOnly = true;
        submit_title.style.removeProperty('display');
        save_title.style.setProperty('display', 'none');
        cancel_title.style.setProperty('display', 'none');
    }
    cancel_title.onclick = () => {
        summary_title_edit.value = original_title;
        resetButton();
    }
    save_title.onclick = () => {
        if(!summary_title_edit) {
            alert("Not Empty Title");
            return;
        }
        editData('name');
        resetButton();
    }
}

const submit_content = document.querySelector('.summary-submit-textarea')
const summary_content_edit = document.querySelector('.summary-content .summary-text');
const save_content = document.querySelector('#save-content');
const cancel_content = document.querySelector('#cancel-content');
let original_content;

submit_content.onclick = () => {
    original_content = summary_content_edit.value;
    summary_content_edit.readOnly = false;
    submit_content.style.setProperty('display', 'none');
    save_content.style.removeProperty('display');
    cancel_content.style.removeProperty('display');

    const resetButton = () => {
        summary_content_edit.readOnly = true;
        submit_content.style.removeProperty('display');
        save_content.style.setProperty('display', 'none');
        cancel_content.style.setProperty('display', 'none');
    }
    cancel_content.onclick = () => {
        summary_content_edit.value = original_content;
        resetButton();
    }
    save_content.onclick = () => {
        if(!summary_content_edit) {
            alert("Not Empty Content");
            return;
        }
        editData('content');
        resetButton();
    }
}

const editData = (type) => {
    fetch(`http://localhost:8080/detail/${id}/edit?type=${type}`,{
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            name: summary_title_edit.value,
            content: summary_content_edit.value
        })
    })
        .then((response) => response.json())
        .then((body) => {
            if(body.status === 'error'){
                alert('수정 실패')
                if(type === 'name')
                    summary_title_edit.value = original_title;
                if(type === 'content')
                    summary_content_edit.value = original_content;
            } else {
                alert('수정 성공')
                if(type === 'name')
                    summary_title_edit.value = body.data.name;
                if(type ==='content')
                    summary_content_edit.value = body.data.summaryContent;
            }

        })
}

