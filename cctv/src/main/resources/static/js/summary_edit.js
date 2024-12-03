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
    fetch(`/detail/${id}/edit?type=${type}`,{
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

summary_body = {
    // "message":"0: The image depicts a street scene in what appears to be an urban area. The street is lined with buildings on both sides, and there are several orange traffic cones placed on the road. The road has two lanes, and there are no visible vehicles on it. On the left side of the image, there is a sidewalk with a person walking along it. The sidewalk is made of bricks, and there are trees and buildings visible on both sides of the street. The buildings on the right side have signs in Korean, indicating that this might be a Korean-speaking area. The overall scene suggests a quiet, possibly residential or commercial street.,2: The image depicts a street scene in an urban area. The street is divided into two lanes, with a yellow line separating them. There are orange traffic cones placed along the road, possibly indicating a construction zone or a restricted area. \n\nOn the left side of the image, there is a sidewalk with a few trees and a few buildings. One of the buildings has a sign in Korean, suggesting that the area is in a Korean-speaking region. There is a person walking on the sidewalk, and another person is standing near a parked car on the right side of the street. \n\nThe background shows more buildings with storefronts, and there are,22: The image depicts an urban street scene with a clear view of the road and surrounding buildings. The road is divided into two lanes, with yellow lines marking the lanes. There are orange traffic cones placed along the road, possibly indicating a construction zone or a temporary road closure. The street is lined with buildings on both sides, including shops with awnings and signs. The buildings have a mix of commercial and residential uses, with some having signage in both Korean and English. The street is relatively quiet, with no visible pedestrians or vehicles. The overall atmosphere suggests a typical day in an urban area with ongoing construction or maintenance work.,29: The image depicts an urban street scene with a clear view of the road and surrounding buildings. The road is divided by a double yellow line, indicating a no-parking zone. There are orange traffic cones placed along the road, possibly marking a construction zone or a restricted area. The street is flanked by buildings on both sides, with some shops and businesses visible. The buildings have signage in Korean, suggesting that the location is in a Korean-speaking region. The street appears to be relatively quiet, with no visible traffic or pedestrians. The overall atmosphere is calm and orderly, typical of a typical city street in a non-urban area.","summary":"Here's a two-sentence summary of the frame times and descriptions:\n\nThe street scene depicts a quiet, possibly residential or commercial street in an urban area with a mix of Korean and English signage, featuring buildings with storefronts, sidewalks, and trees, with no visible vehicles or pedestrians. The overall atmosphere suggests a typical day in an urban area with ongoing construction or maintenance work, marked by orange traffic cones and a clear view of the road and surrounding buildings."
    "message":"0: The image shows a small, neatly organized bedroom. The walls are painted a light color, possibly beige or light gray. There is an air conditioner mounted on the wall above the bed. The bed has a yellow mattress and is covered with a gray blanket. On the bed, there are some clothes, including a green shirt and a white shirt. The room also has a wooden headboard and a drawer under the bed. The overall appearance is tidy and organized.,2: The image shows a bedroom with a bed on the left side, which appears to be unmade. The bed has a yellow mattress and is covered with a white blanket. There is a wooden headboard attached to the wall. On the right side of the bed, there is a piece of clothing hanging on a rack. The wall behind the bed is painted a light blue color. There is an air conditioner mounted on the wall above the bed. The floor is not visible in the image.,4: The image shows a bedroom with a bed on the left side, which has a yellow mattress. The bed is unmade and has a few items on it, including a green cloth and a white cloth. There is a wooden headboard attached to the bed. On the right side of the bed, there is a mattress with a blue and white striped cover. Above the bed, there is an air conditioner mounted on the wall. The wall behind the bed is painted in a light blue color. On the left side of the image, there is a wooden bookshelf with some items on it. The ceiling is white and there is a light,6: The image shows a bedroom with the following features:\n\n1. **Air Conditioning Unit**: There is an air conditioning unit mounted on the wall above the bed.\n2. **Bed**: The bed is made with a yellow and green bedspread and a brown blanket.\n3. **Shelves**: There is a wooden shelf against the wall to the left of the bed.\n4. **Wires**: There are two white wires connected to the wall socket, which is located on the wall to the right of the bed.\n5. **Wall**: The wall is painted in a light blue color.\n6. **Curtains**: There are no,8: The image shows a person standing in a room. The person is wearing a camouflage-patterned t-shirt and black shorts. The room has a bed with a yellow mattress and a wooden headboard. There is a bookshelf with some items on it, and an air conditioner mounted on the wall. The room appears to be well-lit, with natural light coming from a window.,10: The image shows a room with a bed on the floor, a mattress with a green and yellow pattern, and a wooden headboard. There is a white air conditioner mounted on the wall above the bed. The room has a light blue wall and a wooden shelf with some items on it. The floor appears to be made of wood.,12: The image shows a room with a bed in the foreground. The bed has a yellow and green patterned mattress and is covered with a blanket. On the wall above the bed, there is an air conditioner unit mounted on the wall. To the left of the bed, there is a wooden bookshelf with some items on it. The room appears to be a bedroom or a small living area.,14: The image shows a person standing on a bed in a room. The person is wearing a camouflage t-shirt and black shorts. The bed has a yellow mattress and is covered with a green blanket. There is an air conditioner mounted on the wall above the bed. The room has a wooden bookshelf against the wall and a wall socket with a plug. The ceiling has a light fixture.,16: The image shows a person lying on a bed in a room. The bed has a yellow mattress and is placed against a wall. There is an air conditioner mounted on the wall above the bed. The room has a blue wall and a wooden bookshelf against the wall. The person is wearing a camouflage-patterned shirt and black shorts. The room appears to be well-lit, with natural light coming from a window or a light source outside the frame.,20: The image shows a person lying on a bed in a room. The person is wearing a camouflage-patterned shirt and black shorts. The bed has a green mattress and is placed against a wall with a white air conditioning unit mounted on it. The room has a bookshelf with some books and a chair visible in the background. The lighting in the room appears to be natural, possibly from a window outside the frame.,22: The image shows a person standing on a bed in a room. The person is wearing a camouflage t-shirt and black shorts. The bed has a yellow mattress and is positioned against a wall with an air conditioner unit mounted on it. The room has a wooden shelf against the wall and a wall socket visible on the wall. The lighting in the room appears to be natural, possibly from a window outside the frame.,24: The image shows a person standing in a room. The person is wearing a camouflage t-shirt and black underwear. The room has a bed with a yellow and green bedspread, a wooden bookshelf, and a wall socket on the wall. The lighting in the room appears to be natural, possibly from a window.,26: The image appears to be a close-up of a textured surface, possibly a piece of fabric or a wall. The texture is consistent and appears to be made up of horizontal lines or stripes. The color is a muted grayish-white, giving it a somewhat neutral and somewhat abstract appearance. There are no distinct objects or patterns visible in the image, and the focus is on the texture itself.","summary":"Here is a two-sentence summary of the frame times and descriptions:\n\nThe images depict various bedrooms, each with a bed, air conditioning unit, and different wall colors, with some featuring organized and tidy spaces while others appear unmade. The rooms also feature various objects, such as bookshelves, lamps, and clothing, with some images showing a person or people in the room, but the focus remains on the room's layout and decor."
}

document.querySelector(".summary-create-button").onclick = () => {
    summary_content_edit.value = summary_body.summary;
    // editData('content');
    editLogs(summary_body.message);

    // fetch(`https://e059-69-30-85-159.ngrok-free.app/data/1`, {
    //     method: 'POST',
    //     headers: {
    //         "Content-Type": "application/json",
    //         "mode": "no-cors"
    //     },
    //     body: JSON.stringify({
    //         https_url: document.querySelector('.video').getAttribute('src')
    //     })
    // })
    // fetch(url)
    //     .then((response) => response.json())
    //     .then((body) => {
    //         console.log(body);
    //         // summary_content_edit.value = body.summary;
    //         // editData('content');
    //         // editLogs(body.message);
    //     })
    // const url = "https://e059-69-30-85-159.ngrok-free.app/data/1";
    // // const url = "http://127.0.0.1:8000/ping"
    // fetch(url, {
    //     method: "GET",
    // })
    //     .then((response) => {
    //         if (!response.ok) {
    //             throw new Error(`HTTP error! status: ${response.status}`);
    //         }
    //         // 응답 헤더 확인
    //         console.log("Custom Header:", response.headers.get("Custom-Header"));
    //
    //         return response.json(); // JSON 응답 파싱
    //     })
    //     .then((data) => {
    //         console.log("Server Response:", data); // 서버 응답 데이터 출력
    //     })
    //     .catch((error) => {
    //         console.error("Error:", error); // 에러 처리
    //     });
}

function editLogs(logList) {
    const list = logList.split('.,');
    list.sort();
    const summary_log_content = document.querySelector(".summary-log-content")
    let logs = "";
    for(const log of list) {
        logs += `
                    <div class="log">
                        <p class="description">${log}</p>
                    </div>
                `;
    }
    summary_log_content.innerHTML = logs;
}

document.querySelector('.summary-event-check').onclick = async () => {
    document.querySelector(".summary-event .summary-text").value = "이벤트 감지 중...";
    // fetch("https://c115-3-39-140-30.ngrok-free.app/data/", {
    fetch("https://45b1-91-199-227-82.ngrok-free.app/data/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ https_url: document.querySelector('.video').getAttribute('src') }),
    })
        .then((response) => response.json())
        .then((body) => {
            console.log(body);
            document.querySelector(".summary-event .summary-text").value = body.message;
        })
}


