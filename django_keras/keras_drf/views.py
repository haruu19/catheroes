# from rest_framework.decorators import api_view
# from rest_framework.response import Response

# from PIL import Image
# import os, glob, numpy as np
# from keras.models import load_model

# directory = os.getcwd()
# model = load_model(directory + '/keras_drf/cat_model.h5')

# @api_view(['POST'])
# def keras(request):

#     X = []
#     files = request.data['image']
#     img = Image.open(files)
#     img = Image.open(files)
#     # img = img.convent("RGB") # 왜 에러인지 모르겠다
#     img = img.resize((64, 64))
#     data = np.asarray(img)
#     X.append(data)
#     X = np.array(X)
#     prediction = model.predict(X)
#     pre_ans = prediction[0].argmax()
#     if pre_ans == 0: 
#         pre_ans_str = "고등어태비"
#     elif pre_ans == 1:
#         pre_ans_str = "치즈태비"
#     elif pre_ans == 2:
#         pre_ans_str = "검은고양이"
#     elif pre_ans == 3:
#         pre_ans_str = "턱시도"
#     elif pre_ans == 4:
#         pre_ans_str = "삼색이"
#     else: 
#         pre_ans_str = "흰고양이"
#     if prediction[0][0] >= 0.8 : 
#         print("해당 이미지는 " + pre_ans_str + "로 추정됩니다.")
#     elif prediction[0][1] >= 0.8: 
#         print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
#     elif prediction[0][2] >= 0.8: 
#         print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
#     elif prediction[0][3] >= 0.8: 
#         print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
#     elif prediction[0][4] >= 0.8: 
#         print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
#     elif prediction[0][5] >= 0.8: 
#         print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
#     res = pre_ans_str
#     return Response(res)



from rest_framework.decorators import api_view
from rest_framework.response import Response

import cv2
from PIL import Image
import os, glob, numpy as np
from keras.models import load_model

# keras 로드
directory = os.getcwd()
model = load_model(directory + '/keras_drf/cat_model.h5')

# Yolo 로드
net = cv2.dnn.readNet(directory + "/keras_drf/yolov3.weights", directory + "/keras_drf/yolov3.cfg")
classes = []
with open(directory + "/keras_drf/coco.names", "r") as f:
    classes = [line.strip() for line in f.readlines()]
layer_names = net.getLayerNames()
output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]
colors = np.random.uniform(0, 255, size=(len(classes), 3))


@api_view(['POST'])
def keras(request):

    print('@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2')
    ff = request.data['image']
    print("request.data: ", ff)
    # img = Image.open(directory + "/keras_drf/three.jpg")
    # print(img)
    # img = cv2.imdecode(request.data['image'].read(), cv2.IMREAD_UNCHANGED) 
    imge = cv2.imdecode(np.fromstring(request.data['image'].read(), np.uint8), cv2.IMREAD_UNCHANGED)
    # print("img: ", img)
    # ff = cv2.imread(directory + "/keras_drf/three.jpg")
    # print("ff: ",ff)
    # imge = cv2.imread(ff)
    print("imge: ", imge)
    img = cv2.resize(imge, None, fx=0.4, fy=0.4)
    height, width, channels = img.shape
    blob = cv2.dnn.blobFromImage(img, 0.00392, (416, 416), (0, 0, 0), True, crop=False)
    net.setInput(blob)
    outs = net.forward(output_layers)
    class_ids = []
    confidences = []
    boxes = []
    for out in outs:
        for detection in out:
            scores = detection[5:]
            class_id = np.argmax(scores)
            confidence = scores[class_id]
            if confidence > 0.5:
                # Object detected
                center_x = int(detection[0] * width)
                center_y = int(detection[1] * height)
                w = int(detection[2] * width)
                h = int(detection[3] * height)
                # 좌표
                x = int(center_x - w / 2)
                y = int(center_y - h / 2)
                boxes.append([x, y, w, h])
                confidences.append(float(confidence))
                class_ids.append(class_id)

    indexes = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4)
    font = cv2.FONT_HERSHEY_PLAIN
    res = "잘모르겠습니다"
    for z in range(len(boxes)):
        if z in indexes:
            x, y, w, h = boxes[z]
            label = str(classes[class_ids[z]])
            if label == "cat":
                color = colors[z]
                cv2.rectangle(img, (x, y), (x + w, y + h), color, 2)
                cv2.putText(img, label, (x, y + 30), font, 3, color, 3)

                image_PIL = Image.open(ff)
                a, b = image_PIL.size
                a *= 0.4
                b *= 0.4
                a = int(a)
                b = int(b)
                image_PIL_resize = image_PIL.resize((a,  b))
                image_PIL_resize_crop = image_PIL_resize.crop((boxes[z][0],boxes[z][1],boxes[z][0]+boxes[z][2],boxes[z][1]+ boxes[z][3]))
                imgx = image_PIL_resize_crop

                X = []
                # img = Image.open(files)
                # img = img.convent("RGB") # 왜 에러인지 모르겠다
                imgx = imgx.resize((64, 64))
                data = np.asarray(imgx)
                X.append(data)
                X = np.array(X)
                prediction = model.predict(X)
                pre_ans = prediction[0].argmax()
                if pre_ans == 0: 
                    pre_ans_str = "고등어태비"
                elif pre_ans == 1:
                    pre_ans_str = "치즈태비"
                elif pre_ans == 2:
                    pre_ans_str = "검은고양이"
                elif pre_ans == 3:
                    pre_ans_str = "턱시도"
                elif pre_ans == 4:
                    pre_ans_str = "삼색이"
                else: 
                    pre_ans_str = "흰고양이"
                if prediction[0][0] >= 0.8 : 
                    print("해당 이미지는 " + pre_ans_str + "로 추정됩니다.")
                elif prediction[0][1] >= 0.8: 
                    print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
                elif prediction[0][2] >= 0.8: 
                    print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
                elif prediction[0][3] >= 0.8: 
                    print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
                elif prediction[0][4] >= 0.8: 
                    print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
                elif prediction[0][5] >= 0.8: 
                    print("해당 이미지는 " + pre_ans_str + "으로 추정됩니다.")
                res = pre_ans_str
                break
    return Response(res)