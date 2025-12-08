{{- define "todoapp.name" -}}
todoapp
{{- end -}}

{{- define "todoapp.fullname" -}}
{{ include "todoapp.name" . }}
{{- end -}}
