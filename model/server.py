from fastapi import FastAPI
from pydantic import BaseModel
import joblib
import uvicorn
import os

app = FastAPI()

model_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'model.pkl')
model = joblib.load(model_path)

class PredictionRequest(BaseModel):
    study_hours: float
    previous_grade: float

@app.post("/predict")
def predict(data: PredictionRequest):
    scaled_previous_grade = data.previous_grade * 10
    prediction = model.predict([[data.study_hours, scaled_previous_grade]])
    final_score = float(prediction[0]) / 10
    return {"predicted_score": round(final_score, 2)}

if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8000)