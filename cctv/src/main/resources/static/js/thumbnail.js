let videos = document.querySelectorAll(".thumbnail");
for(const video of videos) {
    if(!video.src) continue;
    console.log(video);
    video.muted=true;
    video.preload="auto";

    console.log(document.querySelector('.video-img').style.width);

    let canvas = document.createElement("canvas");
    canvas.width = Number(document.querySelector('.video-img').style.width);
    canvas.height = Number(document.querySelector('.video-img').style.height);

    let ctx = canvas.getContext("2d");
    ctx.drawImage(video, 0, 0);
}