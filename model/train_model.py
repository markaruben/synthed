import pandas as pd
import joblib
import os
from sklearn import pipeline
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_absolute_error, r2_score

def run():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    file_path = os.path.join(script_dir, "student_performance_updated_1000.csv")
    
    df = pd.read_csv(file_path)
    
    df = df.dropna(subset=['FinalGrade'])
    
    pipeline = Pipeline([
        ('imputer', SimpleImputer(strategy='median')),
        ('scaler', StandardScaler()),
        ('model', RandomForestRegressor(n_estimators=100, random_state=42))
    ])
    
    X = df[['StudyHoursPerWeek', 'PreviousGrade']]
    y = df['FinalGrade']
    
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    
    pipeline.fit(X_train, y_train)
    
    preds = pipeline.predict(X_test)
    print(f"Model trained.")
    print(f"MAE: {mean_absolute_error(y_test, preds):.2f}")
    print(f"R2: {r2_score(y_test, preds):.2f}")
    
    save_path = os.path.join(script_dir, 'model.pkl')
    joblib.dump(pipeline, save_path)
    print(f"Model saved to: {save_path}")

if __name__ == "__main__":
    run()