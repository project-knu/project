function template(video) {
    return `
                <div class="video" onclick="location.href='/summary.html?id=${video.videoId}';" >
                    <div class="video-img"><img src="" alt="video"></div>
                    <p class="video-date">${video.createdAt.slice(0,10)}</p>
                    <p class="video-summary">${video.name}</p>
                    <p class="video-description">${video.summaryContent.slice(0,30)+"..."}</p>
                </div>
                `;
}
function formatDate(date) {
    if(date==null) return null;
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
}
function addDays(date, days) {
    let new_date = new Date(date)
    new_date.setDate(date.getDate()+days)
    return new_date
}

const get_videos = (type, page, keyword, start, end) => { // type('today','lastWeek','old','search')
    let url = `http://localhost:8080/search?p=1&page=${page}&size=5`;
    if(keyword) url += `&keyword=${keyword}`;
    if(start) url += `&start=${start}`;
    if(end) url += `&end=${end}`;
    fetch(url)
        .then((response) => response.json())
        .then((body) => {
            if(body.status == 'error') {
                location.replace('http://localhost:8080/login.html')
                alert(body.message)
            }
            const data = body.data

            const today_record = document.querySelector(`.${type}-record .video-second`)
            let videos = "";
            if(body.size > 0)
                for(const d of data) videos += template(d);
            else videos = "No Record";

            today_record.innerHTML = videos;
        })
}

// 오늘의 기록
let today_page = 0;
const get_today_videos = () => { get_videos('today', today_page, null, formatDate(new Date()), formatDate(new Date())) };
const today_previous = document.querySelector(".today-record .video-previous")
const today_next = document.querySelector(".today-record .video-next")
today_previous.onclick = () => { if(today_page > 0) {today_page--; get_today_videos();} }
today_next.onclick = () => { today_page++; get_today_videos(); }

// 지난주 기록
let lastWeek_page = 0;
const get_lastWeek_videos = () => { get_videos('lastWeek', lastWeek_page, null, formatDate(addDays(new Date(), -7)), formatDate(addDays(new Date(),-1))) };
const lastWeek_previous = document.querySelector(".lastWeek-record .video-previous")
const lastWeek_next = document.querySelector(".lastWeek-record .video-next")
lastWeek_previous.onclick = () => { if(lastWeek_page > 0) {lastWeek_page--; get_lastWeek_videos();} }
lastWeek_next.onclick = () => { lastWeek_page++; get_lastWeek_videos(); }

// 오래된 기록
let old_page = 0;
const get_old_videos = () => { get_videos('old', old_page, null, null, formatDate(addDays(new Date(), -8))) };
const old_previous = document.querySelector(".old-record .video-previous")
const old_next = document.querySelector(".old-record .video-next")
old_previous.onclick = () => { if(old_page > 0) {old_page--; get_old_videos();} }
old_next.onclick = () => { old_page++; get_old_videos(); }

// 검색 결과
let search_page = 0;
const get_search_videos = () => {
    let keyword = document.querySelector("#searchCond").value;
    let search_date = null;
    if(document.querySelector('.selected')) {
        search_date = currentDate
        search_date.setDate(Number(document.querySelector('.selected').innerText))
    }
    if(!keyword && !search_date) {
        alert("검색어 혹은 날짜가 있어야 합니다");
        return;
    }
    document.querySelector('.search-record').style.removeProperty("display");
    let search_condition = ""; if(keyword) search_condition += '['+keyword+']'; if(search_date) search_condition += ' ['+formatDate(search_date)+']';
    document.querySelector('.search-record .video-title').innerHTML = `${search_condition} 검색 결과`
    get_videos('search', search_page, keyword, formatDate(search_date), formatDate(search_date));
}
const search_previous = document.querySelector(".search-record .video-previous")
const search_next = document.querySelector(".search-record .video-next")
search_previous.onclick = () => { if(search_page > 0) {search_page--; get_search_videos();} }
search_next.onclick = () => { search_page++; get_search_videos(); }

// 키워드 검색
const search_button = document.querySelector('.search-button');
search_button.onclick = () => {
    search_page = 0;
    get_search_videos();
}