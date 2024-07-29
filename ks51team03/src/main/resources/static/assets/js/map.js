let currentPosition; // 현재 위치를 저장할 변수
let allResults = []; // 모든 검색 결과를 저장할 배열
const resultsPerPage = 15; // 페이지당 표시할 결과 수
let dbPlaces = []; // 데이터베이스 검색 결과를 저장할 배열
let comMapList = /*[[${comMapList}]]*/ [];

// 현재 위치에 마커 찍는 메서드
function onGeoOkay(position) {
    const lat = position.coords.latitude;
    const lon = position.coords.longitude;
    currentPosition = new kakao.maps.LatLng(lat, lon);
    const marker = new kakao.maps.Marker({
        position: currentPosition
    });

    marker.setMap(map);

    const infowindow = new kakao.maps.InfoWindow({
        content: '<div style="padding:5px;z-index:1;">현재위치</div>'
    });
    infowindow.open(map, marker);

    map.setCenter(currentPosition);
}

function onGeoError() {
    alert("I can't find you. No weather for you.");
}

navigator.geolocation.getCurrentPosition(onGeoOkay, onGeoError);

let markers = [];

const mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
const map = new kakao.maps.Map(mapContainer, mapOption);

// 장소 검색 객체를 생성합니다
const ps = new kakao.maps.services.Places();
const infowindow = new kakao.maps.InfoWindow({zIndex: 1});

// 키워드 검색을 요청하는 함수입니다
document.getElementById('searchForm').addEventListener('submit', function (event) {
    event.preventDefault(); // 기본 폼 제출 동작을 막습니다

    const keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }

    // 검색을 진행합니다
    searchPlaces(keyword);
});

function searchPlaces(keyword) {
    // 모든 결과를 초기화합니다
    allResults = [];
    dbPlaces = []; // 검색마다 dbPlaces 초기화

    // 데이터베이스 검색 요청
    $.ajax({
        url: "/map/get_company_list",
        type: "GET",
        data: { keyword: keyword },
        success: function(response) {
            dbPlaces = response || []; // 응답이 없을 경우 빈 배열 할당

            // 각 dbPlace에서 cCode를 추출하여 추가 요청을 보냅니다
            const cCodes = dbPlaces.map(place => place.companyCode);
            getComMapList(cCodes, keyword);

        },
        error: function(error) {
            console.error("Error fetching company list", error);
        }
    });
}

function getComMapList(cCodes, keyword) {
    $.ajax({
        url: "/map/get_com_map_list",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(cCodes),
        success: function(response) {
            comMapList = response || [];
            console.log("comMapList: " + JSON.stringify(comMapList));

            // 카카오에서 장소 검색
            ps.keywordSearch(keyword, placesSearchCB, {page: 1});
        },
        error: function(error) {
            console.error("Error fetching comMapList", error);
        }
    });
}

document.getElementById('menu_wrap').style.display = 'none';

// 장소검색이 완료됐을 때 호출되는 콜백함수입니다
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        allResults = allResults.concat(data); // 카카오 검색 결과를 allResults에 추가

        if (pagination.current < pagination.last && allResults.length < 45) {
            pagination.gotoPage(pagination.current + 1);
        } else {
            combineResults(); // 모든 페이지의 데이터를 다 받았거나 45개를 초과하면 호출
        }
        document.getElementById('menu_wrap').style.display = 'block';
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
    }
}

// 데이터베이스 결과와 카카오 API 결과를 결합하는 함수
function combineResults() {

    if (dbPlaces.length === 0) {
        processResults(); // 결합된 결과를 처리
    } else {
        allResults = allResults.concat(dbPlaces);
        processResults(); // 결합된 결과를 처리
    }
}

// 모든 검색 결과를 처리하는 함수입니다
function processResults() {
    // 거리 기준으로 정렬합니다
    if (currentPosition) {

        allResults.sort(function (a, b) {
            const aDist = getDistance(currentPosition, new kakao.maps.LatLng(a.y, a.x));
            const bDist = getDistance(currentPosition, new kakao.maps.LatLng(b.y, b.x));
            return aDist - bDist;
        });
    }
    // 첫 페이지를 표시합니다
    displayPage(1);
    displayPagination(Math.ceil(allResults.length / resultsPerPage));
}

// 두 지점 간의 거리를 계산하는 함수입니다
function getDistance(position1, position2) {
    const R = 6371; // 지구의 반지름 (단위: km)
    const dLat = (position2.getLat() - position1.getLat()) * Math.PI / 180;
    const dLon = (position2.getLng() - position1.getLng()) * Math.PI / 180;
    const lat1 = position1.getLat() * Math.PI / 180;
    const lat2 = position2.getLat() * Math.PI / 180;

    const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c;
    return distance * 1000; // 결과를 미터로 반환
}

// 특정 페이지를 표시하는 함수입니다
function displayPage(page) {
    const start = (page - 1) * resultsPerPage;
    const end = start + resultsPerPage;
    displayPlaces(allResults.slice(start, end));

    // 페이지 번호 업데이트
    const paginationEl = document.getElementById('pagination');
    const children = paginationEl.children;
    for (let i = 0; i < children.length; i++) {
        if (i + 1 === page) {
            children[i].className = 'on';
        } else {
            children[i].className = '';
        }
    }
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(totalPages) {
    const paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment();

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
    }

    for (let i = 1; i <= totalPages; i++) {
        const el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i === 1) {
            el.className = 'on';
        } else {
            el.onclick = (function (i) {
                return function () {
                    displayPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {

    const listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        infoCompany = document.getElementById('info_wrap'),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds();

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    // 첫 번째 마커의 위치를 저장할 변수
    let firstPlacePosition;
    let listIdx = 0;


    // =================== 데이터베이스 데이터 반복 ======================================
    for (let i = 0; i < dbPlaces.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        const placePosition = new kakao.maps.LatLng(comMapList[i].cmY, comMapList[i].cmX);
        console.log("dbPlaces: " + JSON.stringify(dbPlaces));
        const marker = addMarkerForDb(placePosition);
        const itemEl = getDbListItem(i, dbPlaces[i]); // 검색 결과 항목 Element를 생성합니다

        // 첫 번째 장소의 위치를 저장합니다
        if (i === 0) {
            firstPlacePosition = placePosition;
        }

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기 위해 LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        itemEl.setAttribute('data-index', i);

        (function (marker, title, idx) {
            itemEl.onclick = function () {
                menuEl.style.display = 'none';
                infoCompany.style.display = 'block';
                displayInfowindow(marker, dbPlaces[idx].companyName);
                map.setCenter(marker.getPosition());
            };
        })(marker, dbPlaces[i], i);

        fragment.appendChild(itemEl);
    }
    // =================== 데이터베이스 데이터 반복 end ======================================
    // DB 항목을 Element로 반환하는 함수입니다
    function getDbListItem(index, dbPlace) {
        let el = document.createElement('li'),
            itemStr = '<span class="markerbg2"></span>' +
                '<div class="info" onclick="showDetails(' + listIdx + ', true)">' +
                '   <h5>' + dbPlace.companyName + '<h5>';

        if (dbPlace.road_address_name) {
            itemStr += '    <span>' + dbPlace.road_address_name + '</span>' +
                '   <span class="jibun gray">' + dbPlace.companyAddr + '</span>';
        } else {
            itemStr += '    <span>' + dbPlace.companyAddr + '</span>';
        }

        itemStr += '  <span class="tel">' + dbPlace.companyPhone + '</span>' +
            '</div>';

        el.innerHTML = itemStr;
        el.className = 'item';
        listIdx++;
        return el;
    }

    // =================== 카카오 데이터 반복 start =========================================
    for (let i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        const placePosition2 = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker2 = addMarkerForKakao(placePosition2, i),
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
        if (dbPlaces.length === 0) {
            // 첫 번째 장소의 위치를 저장합니다
            if (i === 0) {
                firstPlacePosition = placePosition2;
            }

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기 위해 LatLngBounds 객체에 좌표를 추가합니다
            bounds.extend(placePosition2);
        }

        itemEl.setAttribute('data-index', i);

        (function (marker2, title, idx) {
            itemEl.onclick = function () {
                menuEl.style.display = 'none';
                infoCompany.style.display = 'block';
                displayInfowindow(marker2, title);
                map.setCenter(marker2.getPosition());
            };
        })(marker2, places[i].place_name, i);

        fragment.appendChild(itemEl);
    }
    // =================== 카카오 데이터 반복 end ===========================================

    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);

    // 최대 확대 레벨 제한
    const maxZoomLevel = 3; // 최대 확대 레벨 설정
    if (map.getLevel() > maxZoomLevel) {
        map.setLevel(maxZoomLevel);
    }

    // 첫 번째 마커의 위치를 지도의 중심으로 설정합니다
    if (firstPlacePosition) {
        map.setCenter(firstPlacePosition);
    }

    // 카카오 항목을 Element로 반환하는 함수입니다
    function getListItem(index, places) {
        let el = document.createElement('li'),
            itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
                '<div class="info" onclick="showDetails(' + listIdx + ', false)">' +
                '   <h5>' + places.place_name + '</h5>';

        if (places.road_address_name) {
            itemStr += '    <span>' + places.road_address_name + '</span>' +
                '   <span class="jibun gray">' + places.address_name + '</span>';
        } else {
            itemStr += '    <span>' + places.address_name + '</span>';
        }

        itemStr += '  <span class="tel">' + places.phone + '</span>' +
            '</div>';

        el.innerHTML = itemStr;
        el.className = 'item';
        listIdx++;
        return el;
    }
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarkerForKakao(position, idx) {
    const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}
// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarkerForDb(position) {
    const imageSrc = '/assets/img/map_marker.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(64,69),  // 마커 이미지의 크기
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 50), // 스프라이트 이미지의 크기
            offset: new kakao.maps.Point(20, 40) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

function displayInfowindow(marker, title) {
    const content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
    }
}

function showDetails(index, isDB) {
    const infoWrap = document.getElementById('info_wrap');
    const menuWrap = document.getElementById('menu_wrap');
    let cCode;

    const placeNameDb = document.getElementById('placeNameDb');
    const placeNameKakao = document.getElementById('placeNameKakao');
    const placeIcon = document.getElementById('placeIcon');

    // 기존 정보를 초기화합니다.
    placeNameDb.value = '';
    placeNameDb.href = '';
    placeNameDb.style.display = 'none';
    placeNameKakao.value = '';
    placeNameKakao.style.display = 'none';
    placeIcon.src = ''; // 이미지 초기화

    document.getElementById('parking').value = '주차장: 알수없음';
    document.getElementById('placeAddress').value = '';
    document.getElementById('placePhone').value = '';
    document.getElementById('employee_count').value = '직원 수: 알수없음';
    document.getElementById('placeRating').value = '별점: 알수없음';
    document.getElementById('placeReviews').value = '리뷰: 알수없음';
    document.querySelector('.review_list').innerHTML = '';
    document.getElementById('write-question-link').style.display = 'none';
    document.getElementById('write-review-link').style.display = 'none';

    if (isDB) {
        const places = dbPlaces[index];
        cCode = places.companyCode; // cCode 설정
        placeNameDb.innerText  = places.companyName;
        placeNameDb.href = "/map/map_company_info?cCode=" + cCode;
        placeNameDb.style.display = 'block';

        document.getElementById('parking').value = '주차장: ' + (places.companyParking ? 'O' : 'X');
        document.getElementById('placeAddress').value = places.companyAddr;
        document.getElementById('placePhone').value = places.companyPhone;
        document.getElementById('employee_count').value = '직원 수: ' + places.companyStfCount;

        document.getElementById('write-question-link').setAttribute('href', '/map/map_write_question?cCode=' + cCode);
        document.getElementById('write-review-link').setAttribute('href', '/map/map_write_review?cCode=' + cCode);
        document.getElementById('write-question-link').style.display = 'block';
        document.getElementById('write-review-link').style.display = 'block';

        // AJAX 요청으로 리뷰 데이터와 이미지 가져오기
        $.ajax({
            url: "/map/getCompanyInfo",
            type: "GET",
            data: { cCode: cCode },
            success: function(response) {
                document.getElementById('placeRating').value = '별점: ' + (response.avgReviewScore !== null ? response.avgReviewScore : '없음');
                document.getElementById('placeReviews').value = '리뷰 수: ' + (response.reviewCount !== null ? response.reviewCount : '없음');

                if (response.comImg && response.comImg.length > 0) {
                    placeIcon.src = response.comImg[0]; // 이미지 설정
                } else {
                    placeIcon.src = '/path/to/default/image.png'; // 기본 이미지 설정
                }

                const reviewList = document.querySelector('.review_list');
                reviewList.innerHTML = ''; // 기존 리뷰 목록 초기화
                if (response.reviews && response.reviews.length > 0) {
                    const reviewsToShow = response.reviews.slice(0, 3);
                    reviewsToShow.forEach(function(review) {
                        const reviewDiv = document.createElement('div');
                        reviewDiv.className = 'review';
                        // 리뷰 텍스트 추가
                        const reviewText = document.createElement('p');
                        reviewText.innerText = review.memberId + ': ' + review.revContent;
                        reviewDiv.appendChild(reviewText);
                        // 이미지 추가
                        if (review.filePath) {
                            const reviewImage = document.createElement('img');
                            reviewImage.src = review.filePath;
                            reviewImage.style.display = 'block'; // 이미지 블록 요소로 설정
                            reviewImage.style.width = '80px'; // 이미지 크기 조정
                            reviewImage.style.height = '80px'; // 이미지 비율 유지
                            reviewDiv.appendChild(reviewImage);
                        }
                        reviewList.appendChild(reviewDiv);
                    });
                } else {
                    reviewList.innerText = '리뷰 없음';
                }
            },
            error: function(error) {
                console.error("Error fetching company info", error);
            }
        });

    } else {
        const places = allResults[index - dbPlaces.length];
        cCode = places.id; // cCode 설정
        placeNameKakao.value = places.place_name;
        placeNameKakao.style.display = 'block';

        document.getElementById('placeAddress').value = places.address_name;
        document.getElementById('placePhone').value = places.phone;

        const reviewList = document.querySelector('.review_list');
        reviewList.innerHTML = '리뷰 없음'; // 기존 리뷰 목록 초기화
    }

    menuWrap.style.display = 'none';
    infoWrap.style.display = 'block';
}



document.getElementById('returnMenu_wrap').onclick = function () {
    document.getElementById('info_wrap').style.display = 'none';
    document.getElementById('menu_wrap').style.display = 'block';
};
