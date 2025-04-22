from flask import Flask, request, jsonify
import openai
import json

app = Flask(__name__)

# 🔑 복사한 OpenAI API 키를 여기에 붙여넣어 주세요!
import os
# openai.api_key = os.getenv("OPENAI_API_KEY")

@app.route('/api/analyze', methods=['POST'])
def analyze_code():
    data = request.get_json()

    code = data.get("code")
    codeTestSeq = data.get("codeTestSeq")
    subject_list = data.get("subjectList")  # ✅ Java에서 넘어온 전체 과목 목록

    print(f"[AI 서버] 코드 받음 (문제번호: {codeTestSeq})")
    print(f"[과목 목록] {subject_list}")

    # ✅ GPT에게 보낼 프롬프트 구성
    prompt = f"""
    너는 초보 개발자의 코드를 분석하는 AI야.

    우리가 가르치는 전체 과목 리스트는 다음과 같아:
    {subject_list}

    사용자가 제출한 문제 번호는 {codeTestSeq}번이고,
    아래는 그 사람이 제출한 Java 코드야:

    ```java
    {code}
    ```

    다음 기준으로 분석해서 아래 JSON 형식으로 응답해줘:

    1. 통과 여부 (Y or N)
    2. 코드 피드백 (자세히!)
    3. 부족한 개념은 위 과목 리스트에서 골라서 알려줘 (콤마로 구분)
    4. 테스트 케이스 통과 수와 총 개수
    5. 실행 시간 (0.01 단위로 예측해도 괜찮아)

    응답 예시:

    {{
        "isPassed": "Y",
        "aiFeedback": "반복문은 잘 사용했으나 변수명이 모호합니다.",
        "weakConcepts": "변수명, 예외 처리",
        "testCasePassed": 4,
        "totalTestCases": 5,
        "executionTime": 0.41
    }}
    """

    try:
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "너는 친절한 코드 리뷰어야"},
                {"role": "user", "content": prompt}
            ],
            temperature=0.7
        )

        reply = response.choices[0].message['content']
        print("[GPT 응답]\n", reply)

        parsed = json.loads(reply)
        return jsonify(parsed)

    except Exception as e:
        print("GPT 호출 에러:", e)
        return jsonify({
            "isPassed": "N",
            "aiFeedback": "AI 분석 실패",
            "weakConcepts": "",
            "testCasePassed": 0,
            "totalTestCases": 0,
            "executionTime": 0.0
        })

# ✅ 꼭 함수 밖에 있어야 합니다!
if __name__ == '__main__':
    app.run(port=5000)
