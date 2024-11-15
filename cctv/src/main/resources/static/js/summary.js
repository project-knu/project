function T_remove(time) {
    return time.slice(0, 10) + " " + time.slice(11, 20);
}

let query = window.location.search;
let param = new URLSearchParams(query);
let id = param.get('id');
fetch("http://localhost:8080/detail/"+id)
    .then((response) => response.json())
    .then((body) => {

        if(body.status == 'error') {
            location.replace('http://localhost:8080/index.html')
            alert(body.message)
        }
        const data = body.data

        // video
        const video = document.querySelector(".video")
        video.setAttribute("src", data.url);
        // video.setAttribute("src", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");

        const summary_title = document.querySelector(".summary-title .summary-text");
        const summary_date = document.querySelector(".summary-date .summary-text");
        const summary_content = document.querySelector(".summary-content .summary-text");
        const summary_create_date = document.querySelector(".summary-create-date .summary-text");
        const summary_location = document.querySelector(".summary-location .summary-text");

        summary_title.setAttribute("value", data.name);
        summary_date.innerText = T_remove(data.summaryModifiedAt);
        summary_content.innerHTML = data.summaryContent;
        summary_create_date.innerText = T_remove(data.createdAt);
        summary_location.innerText = data.location;

        // log
        const summary_log_content = document.querySelector(".summary-log-content")
        let logs = "";
        for(const log of data.logList) {
            logs += `
                    <div class="log">
                        <p class="time">${log.time.slice(11, 16)}</p>
                        <p class="description">${log.content}</p>
                    </div>
                `;
        }
        summary_log_content.innerHTML = logs;

    })