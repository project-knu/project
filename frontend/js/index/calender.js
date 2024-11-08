const calendarTable = document.querySelector("#calendarTable tbody");
        const monthYearDisplay = document.querySelector("#monthYear");
        const prevButton = document.querySelector("#prev");
        const nextButton = document.querySelector("#next");
    
        let selectedDay;
        let currentDate = new Date();
    
        function renderCalendar() {
            calendarTable.innerHTML = ""; // 테이블 초기화
            const year = currentDate.getFullYear();
            const month = currentDate.getMonth();
    
            // 월/연도 표시 업데이트
            monthYearDisplay.textContent = `${year}년 ${month + 1}월`;
    
            // 첫 번째 날짜와 마지막 날짜 계산
            const firstDay = new Date(year, month, 1).getDay();
            const lastDate = new Date(year, month + 1, 0).getDate();
    
            let date = 1;
            for (let i = 0; i < 6; i++) { // 최대 6주 표시
                const row = document.createElement("tr");
    
                for (let j = 0; j < 7; j++) { // 일요일부터 토요일까지
                    const cell = document.createElement("td");
    
                    if (i === 0 && j < firstDay) {
                        // 첫째 주에서 첫 날 이전은 빈 셀로
                        row.appendChild(cell);
                    } else if (date > lastDate) {
                        // 마지막 날짜를 넘으면 멈춤
                        break;
                    } else {
                        cell.textContent = date;
                        cell.addEventListener("click", function() {
                            if (selectedDay) {
                                selectedDay.classList.remove("selected");
                            }
                            cell.classList.add("selected");
                            selectedDay = cell;
                        });
                        row.appendChild(cell);
                        date++;
                    }
                }
                calendarTable.appendChild(row);
            }
        }
    
        // 이전 달로 이동
        prevButton.addEventListener("click", function() {
            currentDate.setMonth(currentDate.getMonth() - 1);
            renderCalendar();
        });
    
        // 다음 달로 이동
        nextButton.addEventListener("click", function() {
            currentDate.setMonth(currentDate.getMonth() + 1);
            renderCalendar();
        });
    
        renderCalendar(); // 초기 로드 시 달력 표시