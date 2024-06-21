import { useEffect, useState } from "react";
import "./KakaoMap.css";

export default function KakaoMap({
  initialSearchValue,
  onSelect,
  onConfirm,
  onClose,
}) {
  const [map, setMap] = useState(null);
  const [searchValue, setSearchValue] = useState(initialSearchValue);
  const [selectedPlace, setSelectedPlace] = useState(null);

  // Kakao 지도 API 스크립트를 동적으로 로드
  useEffect(() => {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${
      import.meta.env.VITE_KAKAO_API_KEY
    }&libraries=services&autoload=false`;
    script.async = true;
    document.head.appendChild(script);

    script.onload = () => {
      window.kakao.maps.load(() => {
        const container = document.getElementById("map");
        const options = {
          center: new window.kakao.maps.LatLng(33.450701, 126.570667),
          level: 3,
        };
        const map = new window.kakao.maps.Map(container, options);
        setMap(map);
      });
    };

    return () => {
      document.head.removeChild(script);
    };
  }, []);

  // 지도와 검색어가 준비되면 검색 수행
  useEffect(() => {
    if (map && searchValue) {
      performSearch(searchValue);
    }
  }, [map, searchValue]);

  // 장소 검색 수행 함수
  const performSearch = (query) => {
    const places = new window.kakao.maps.services.Places();
    places.keywordSearch(query, (data, status, pagination) => {
      if (status === window.kakao.maps.services.Status.OK) {
        const bounds = new window.kakao.maps.LatLngBounds();
        for (let i = 0; i < data.length; i++) {
          const place = data[i];
          displayMarker(place);
          bounds.extend(new window.kakao.maps.LatLng(place.y, place.x));
        }
        map.setBounds(bounds);
      }
    });
  };

  // 마커 표시 함수
  const displayMarker = (place) => {
    const marker = new window.kakao.maps.Marker({
      map: map,
      position: new window.kakao.maps.LatLng(place.y, place.x),
    });

    window.kakao.maps.event.addListener(marker, "click", () => {
      setSelectedPlace(place);
    });
  };

  // 검색 버튼 클릭 시 검색 수행
  const handleSearch = () => {
    performSearch(searchValue);
  };

  // 장소 확인 버튼 클릭 시 부모 컴포넌트에 선택된 장소 전달
  const handleConfirm = () => {
    onSelect(selectedPlace);
    onConfirm();
  };

  return (
    <div className="map-popover-overlay">
      <div className="map-popover">
        <button className="close-button" onClick={onClose}>
          X
        </button>
        <div>
          <input
            type="text"
            value={searchValue}
            onChange={(e) => setSearchValue(e.target.value)}
            placeholder="장소를 입력하세요"
            style={{ width: "100%", marginBottom: "10px" }}
          />
          <button onClick={handleSearch} style={{ marginBottom: "10px" }}>
            검색
          </button>
        </div>
        <div
          id="map"
          style={{ width: "100%", height: "300px", marginTop: "10px" }}
        ></div>
        {selectedPlace && (
          <div className="selected-place-info">
            <p>선택된 장소: {selectedPlace.place_name}</p>
            <button onClick={handleConfirm}>확인</button>
          </div>
        )}
      </div>
    </div>
  );
}
